## PROPINQ-114: Registro y verificación de email en DigitalOcean

Corrige el flujo de registro en producción donde DigitalOcean bloquea SMTP, Spring Security rechazaba las nuevas rutas de auth y el correo de activación usaba magic links incompatibles con el frontend.

### Arquitectura modular del feature

- Unificación de autenticación bajo el prefijo `/api/v1/auth` en backend y tests.
- Activación de cuenta desacoplada del UUID en URL: se conserva `tokenId` interno y se expone `verificationCode` de 6 dígitos al usuario.
- Envío de correos transaccionales migrado de JavaMail/SMTP a API HTTP de Resend (puerto 443).

### Contrato API (DTOs y endpoints)

- `POST /api/v1/auth/signup|login|check-token|refresh-token` (antes `/auth/*`).
- `POST /api/v1/users/activate` recibe `{ email, verificationCode }` en lugar de `/{userId}/activate` con UUID.
- `POST /api/v1/users/resend-activation-email` sin cambios de contrato.
- Plantilla de email de activación muestra código numérico en lugar de enlace con `activationToken`.

### Modelos y persistencia

- Campo nuevo `verificationCode` en entidad `Token` (sin alterar PK `tokenId`).
- `ITokenRepository`: búsqueda por `verificationCode` y email de usuario asociado.

### Controllers, seguridad y casos de uso

- `IAuthController`: `@RequestMapping("/api/v1/auth")`.
- `Endpoints.java`: rutas públicas `/api/v1/auth/**` y endpoints de activación bajo `/api/v1/users/*`.
- `ActivateUserUseCase`, `SaveUserUseCase`, `ResendActivation*` y `UserActivationService` adaptados al código de verificación.
- `ActivationEmailSender` / `IActivationEmailSender` centralizan el envío del mail de activación.

### Servicios e integraciones

- `EmailService`: cliente HTTP (`HttpClient`) contra `https://api.resend.com/emails` usando `MAIL_PASSWORD` como API key.
- Remitente dinámico: `onboarding@resend.dev` en sandbox o dominio verificado (`no-reply@propinq.online`) según `spring.mail.username`.

### Tests

- `SignupControllerTest` valida registro en `POST /api/v1/auth/signup`.

### Errores solucionados

- Timeout SMTP por bloqueo de puertos 25/465/587/2525 en DigitalOcean.
- HTTP 403 en signup por rutas no permitidas en Spring Security tras mover auth a `/api/v1`.
- Correos que reportaban éxito en UI pero fallaban en background (`@Async`).
- Loop de activación por magic link que redirigía a reenvío de email sin ingresar código.
- Errores 422/403 de Resend por `from` inválido o dominio no verificado.

### Archivos o módulos impactados

- Módulo: `shared/services/EmailService`
- Impacto: integración Resend HTTP
- Módulo: `auth/controllers`, `config/utils/Endpoints`
- Impacto: prefijo `/api/v1/auth` y permisos públicos
- Módulo: `users/*` (models, repositories, use cases, utils/EmailBuilder)
- Impacto: código de verificación de 6 dígitos y activación por email

### Validación

- Pruebas manuales: registro, reenvío de código, activación e inicio de sesión en `propinq.online` con dominio Resend verificado.
- Pruebas automáticas: `SignupControllerTest` (WebMvcTest del endpoint de signup).
