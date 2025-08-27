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
````

Finally, start the application using Docker Compose:
```bash
docker-compose -f docker-compose.dev.yaml up --build -d
````

## Importante sobre scripts y formato de fin de línea (LF)

- Asegúrate de que todos los scripts `.sh` (como `docker-entrypoint.dev.sh`) estén guardados en formato de fin de línea **LF** (no CRLF).
- El Dockerfile convierte automáticamente el script a formato LF usando `dos2unix` durante el build, pero es recomendable que tu editor esté configurado para guardar en LF para evitar problemas en otros entornos.
- En VS Code, puedes forzar esto agregando en tu configuración:
  ```json
  "files.eol": "\n"
  ```
- Si tienes problemas de permisos o de "no such file or directory" al ejecutar el entrypoint, revisa primero el formato de fin de línea del script.