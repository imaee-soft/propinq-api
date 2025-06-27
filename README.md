# PropInq

PropInq es una plataforma web responsive que centraliza la información de propiedades y facilita la interacción entre propietarios e inquilinos en el ámbito de los alquileres, garantizando comodidad y rapidez en la búsqueda de alquileres entre múltiples opciones.

## Instalación y Ejecución

Para instalar el proyecto, sigue estos pasos:

1. Clona el repositorio: `git clone git@github.com:imaee-soft/propinq-api.git`
2. Navega hacia el directorio: `cd propinq-api`
3. Muévete a la rama de desarrollo: `git checkout dev`

#### Ejecución con Maven

Para ejecutar el proyecto utilizando sólo Maven, sigue estos pasos:

1. Construye e instala el proyecto: `mvn clean install`
2. Ejecuta el proyecto: `mvn spring-boot:run`
3. Prueba la API utilizando Postman o algún cliente HTTP en `http://localhost:8080`
4. Prueba la documentación de Swagger en `http://localhost:8080/swagger-ui.html`

Recuerda que si ejecutas el proyecto directamente con Maven debes tener previamente configuradas las instancias de MySQL y MongoDB y establecer la conexión a las mismas en el archivo [application.yaml](src/main/resources/application.yaml).

#### Ejecución con Docker

1. Ejecuta el proyecto desde docker-compose:
   2. Si posees la versión 1.x.x de docker-compose :`docker-compose -f docker-compose.dev.yaml up --build`
   3. Si posees la versión 2.x.x de docker-compose :`docker compose -f docker-compose.dev.yaml up --build`

El script docker-compose automáticamente levanta las imágenes de MySQL y MongoDB y permite ejecutar la aplicación sin inconvenientes. En el entorno de desarrollo, además, permite cargar automáticamente el proyecto cuando se realice algún cambio utilizando Spring DevTools.

#### Documentación

Para acceder a la documentación dinámica del proyecto con Swagger, ingresa a `http://localhost:8080` en tu navegador.