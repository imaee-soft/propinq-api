### Building and running your application

#### Frontend
First, navigate to the frontend repository (`propinq-frontend`) and build the frontend image:
```bash
docker build -f Dockerfile.dev -t propinq-frontend .
```

#### Backend
Next, navigate to the backend repository (`propinq-api`) and prepare the Maven wrapper:
```bash
mvn wrapper:wrapper
```

Build the backend image:
```bash
docker build -f Dockerfile.dev -t propinq-api .
```

Start the application using Docker Compose:
```bash
docker-compose -f docker-compose.dev.yaml up --build -d
```

## Perfiles y configuración de entorno

- El archivo `.env` define la variable `SPRING_PROFILES_ACTIVE` para seleccionar el perfil de Spring Boot:
  - `SPRING_PROFILES_ACTIVE=dev` para desarrollo local (usa `application-dev.yaml`).
  - `SPRING_PROFILES_ACTIVE=docker` para ejecución en contenedor (usa `application-docker.yaml`).
  - `SPRING_PROFILES_ACTIVE=prod` para ejecución local con configuración para producción (usa `application-prod.yaml`).

  - Si no defines la variable, se usa `application.yaml`.

- Los archivos de configuración:
  - `application.yaml`: configuración base y valor por defecto del perfil.
  - `application-dev.yaml`: usa `localhost` como host de las bases de datos para desarrollo local.
  - `application-docker.yaml`: usa los nombres de los servicios de Docker Compose (`mysql-db`, `mongodb`) como host de las bases de datos para ejecución en contenedor.
  - `application-prod.yaml`: usa `localhost` como host de las bases de datos para desarrollo local y configuraciones de producción.
  - `application-test.yaml` (perfil `test`): usado solo al ejecutar tests; H2 en memoria y MongoDB de test. **No toca MySQL real.**

### Ejecutar tests en Docker

Los tests deben correr con perfil `test` para usar H2 en memoria y no conectar a MySQL. Usa `--no-deps` para no levantar MySQL/MongoDB (evita conflicto si ya existen contenedores):

```bash
docker compose -f docker-compose.dev.yaml run --rm --no-deps -e SPRING_PROFILES_ACTIVE=test api ./mvnw test
```

En varias líneas (bash/zsh):

```bash
docker compose -f docker-compose.dev.yaml run --rm --no-deps \
  -e SPRING_PROFILES_ACTIVE=test \
  api ./mvnw test
```

**Regla:** Los unit tests no levantan Spring (`@SpringBootTest`). Se usan solo tests con Mockito (`@ExtendWith(MockitoExtension.class)`).

## Importante sobre scripts y formato de fin de línea (LF)

- Asegúrate de que todos los scripts `.sh` (como `docker-entrypoint.dev.sh`) estén guardados en formato de fin de línea **LF** (no CRLF).
- El Dockerfile convierte automáticamente el script a formato LF usando `dos2unix` durante el build, pero es recomendable que tu editor esté configurado para guardar en LF para evitar problemas en otros entornos.
- En VS Code, puedes forzar esto agregando en tu configuración:
  ```json
  "files.eol": "\n"
  ```
- Si tienes problemas de permisos o de "no such file or directory" al ejecutar el entrypoint, revisa primero el formato de fin de línea del script.