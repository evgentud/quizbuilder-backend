# Quizty

## Local deployment
### 1. Requirements:
- JDK 21
- Docker Compose version 2.20 and later, and Docker Desktop version 4.22 and later
- Docker (for running integration tests and local deployment)

### 2. Generate SSL certificates for local development using script:
Linux:
```bash
cd ./scripts
./generate_ssl_certs.sh
```

Windows:
```bash
cd .\scripts
generate_ssl_certs.bat
```
### 3. Create keystores for local development using script:

Linux:
```bash
cd ./scripts
./create_keystore.sh
```

Windows:
```bash
cd .\scripts
create_keystore.bat
```

### 4. Run the keycloak server using docker-compose:

```bash
cd docker/thirdparty/keycloak
docker-compose --env-file .env.local.dev up
```

### 5. Run the proxy server using docker-compose:

```bash
cd docker/proxy
docker-compose --env-file .env.local.dev up
```

use `--env-file .env.local` if you are running backend and frontend in docker\
use `--env-file .env.local.dev` if you are running backend and frontend on the host machine\
Change the `BACKEND_HOST` and `FRONTEND_HOST` variables in the `docker/thirdparty/proxy/.env.local.dev` file to the host machine IP address

### 6. Building project
```
./gradlew clean build
```

### Keycloack config
Keycload admin URL: https://localhost:8443/auth/admin/
Username/password: admin/admin