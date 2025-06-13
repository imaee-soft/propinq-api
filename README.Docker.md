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

Finally, start the application using Docker Compose:
```bash
docker-compose -f docker-compose.dev.yaml up --build -d
```
