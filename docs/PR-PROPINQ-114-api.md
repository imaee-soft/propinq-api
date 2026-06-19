## PROPINQ-114: Registro, email y enrutamiento API en producción

Corrige el flujo de registro en DigitalOcean (SMTP bloqueado, auth bajo `/api/v1`, activación por código) y unifica el prefijo `/api/v1` en controladores y Spring Security para que Nginx enrute correctamente las peticiones al backend.

### Arquitectura modular del feature

- Unificación de autenticación bajo el prefijo `/api/v1/auth` en backend y tests.
- Activación de cuenta desacoplada del UUID en URL: se conserva `tokenId` interno y se expone `verificationCode` de 6 dígitos al usuario.
- Envío de correos transaccionales migrado de JavaMail/SMTP a API HTTP de Resend (puerto 443).
- Constante global `Endpoints.API = "/api/v1"` usada en `@RequestMapping` de los 15 controladores y en listas de Spring Security.

### Contrato API (DTOs y endpoints)

- `POST /api/v1/auth/signup|login|check-token|refresh-token` (antes `/auth/*`).
- `POST /api/v1/users/activate` recibe `{ email, verificationCode }` en lugar de `/{userId}/activate` con UUID.
- `POST /api/v1/users/resend-activation-email` sin cambios de contrato.
- Plantilla de email de activación muestra código numérico en lugar de enlace con `activationToken`.
- `IParameterController` y demás controladores exponen rutas bajo `/api/v1/*` de forma consistente.

### Modelos y persistencia

- Campo nuevo `verificationCode` en entidad `Token` (sin alterar PK `tokenId`).
- `ITokenRepository`: búsqueda por `verificationCode` y email de usuario asociado.

### Controllers, seguridad y casos de uso

- Todos los `@RequestMapping` usan `Endpoints.API + "/..."` en lugar de strings hardcodeados.
- `Endpoints.java`: `PARAMETERS_ENDPOINTS` y `RETRIEVE_ENDPOINTS` con prefijo `/api/v1/`; acceso público a `/api/v1/neighborhoods/**`.
- `ActivateUserUseCase`, `SaveUserUseCase`, `ResendActivation*` y `UserActivationService` adaptados al código de verificación.
- `ActivationEmailSender` / `IActivationEmailSender` centralizan el envío del mail de activación.

### Servicios e integraciones

- `EmailService`: cliente HTTP (`HttpClient`) contra `https://api.resend.com/emails` usando `MAIL_PASSWORD` como API key.
- Remitente dinámico: `onboarding@resend.dev` en sandbox o dominio verificado (`no-reply@propinq.online`) según `spring.mail.username`.

### URLs y routing

- Nginx en producción solo proxifica rutas que comienzan con `/api/`; sin el prefijo, el SPA devolvía `index.html` y el interceptor de Angular fallaba al parsear HTML como JSON.
- Centralizar `/api/v1` en `Endpoints.API` reduce el riesgo de desalineación entre controladores y reglas de seguridad.

### Tests

- `SignupControllerTest` valida registro en `POST /api/v1/auth/signup`.
- `UserControllerTest` cubre activación y reenvío de código.

### Errores solucionados

- Timeout SMTP por bloqueo de puertos 25/465/587/2525 en DigitalOcean.
- HTTP 403 en signup y en `/parameters` / `/neighborhoods` por rutas no permitidas o sin prefijo `/api/v1`.
- Correos que reportaban éxito en UI pero fallaban en background (`@Async`).
- Loop de activación por magic link incompatible con el frontend.
- Errores 422/403 de Resend por `from` inválido o dominio no verificado.
- Error genérico en producción por peticiones sin `/api/v1` enrutadas al contenedor del frontend.

### Archivos o módulos impactados

- Módulo: `shared/services/EmailService` — integración Resend HTTP.
- Módulo: `config/utils/Endpoints` — constante `API` y permisos públicos.
- Módulo: `auth/controllers`, `parameters`, `neighborhoods` y resto de interfaces de controlador — `@RequestMapping(Endpoints.API + ...)`.
- Módulo: `users/*` — código de verificación de 6 dígitos y activación por email.

### Validación

- Pruebas manuales: registro, reenvío de código, activación e inicio de sesión en `propinq.online`.
- Producción: `GET /api/v1/parameters/max-price`, `/api/v1/properties`, `/api/v1/neighborhoods` → HTTP 200.
- Pruebas automáticas: `SignupControllerTest`, `UserControllerTest`.
