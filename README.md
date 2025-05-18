# PropInq

PropInq es una plataforma web responsive que centraliza la información de propiedades y facilita la interacción entre propietarios e inquilinos en el ámbito de los alquileres, garantizando comodidad y rapidez en la búsqueda de alquileres entre múltiples opciones.

## Instalación y Ejecución

Para instalar el proyecto, sigue estos pasos:

1. Clona el repositorio: `git clone git@github.com:imaee-soft/propinq-api.git`
2. Navega hacia el directorio: `cd propinq-api`

#### Ejecución con Maven

Para ejecutar el proyecto utilizando sólo Maven, sigue estos pasos:

1. Construye e instala el proyecto: `mvn clean install`
2. Ejecuta el proyecto: `mvn spring-boot:run`
3. Prueba la API utilizando Postman o algún cliente HTTP en `http://localhost:8080`

Recuerda que si ejecutas el proyecto directamente con Maven debes tener previamente configuradas las instancias de MySQL y MongoDB y establecer la conexión a las mismas en el archivo [application.yaml](src/main/resources/application.yaml).

#### Ejecución con Docker

1. Ejecuta el proyecto desde docker-compose: `docker-compose up --build`

El script docker-compose automáticamente levanta las imágenes de MySQL y MongoDB y permite ejecutar la aplicación sin inconvenientes.