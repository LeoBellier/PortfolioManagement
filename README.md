# PortfolioManagement

## Project description
This proyect is a portfolio investment manager, designed for help users to manages his investments, make transactions and monitoring their portfolios performance. The application is using Java with Spring Boot in backend, PostgreSQL as database, Redis for caching and Kafka for interaction betweend services. The frontend is in ReactJS

## Project Objetive
The objective of this project is to show my habilities developing, managing financial data and deploying a distributed application, offering a robust and scalable application for portfolio management of any small investor

## Main Features
- **Authentication and authorization:** Implementing JWT for management of authentication and authorization of users.
- **User Management:** Sign in, login and management of profile of user.
- **Portfolio Management:** Creation, visualization and update of portfolios.
- **Transactions:** Performing stock buying and selling transactions with messaging through Kafka.
- **Consulta de Datos Financieros:** Integración con una API externa para obtener precios de acciones en tiempo real.
- **Rendimiento y Escalabilidad:** Uso de Redis para cacheo y Kafka para mensajería de alta performance.

## Tecnologías Utilizadas
- **Backend:** Java (Spring Boot)
- **Base de Datos:** PostgreSQL
- **Cache:** Redis
- **Mensajería:** Apache Kafka
- **Frontend:** ReactJS
- **Autenticación:** JWT (JSON Web Tokens)
- **Construcción:** Gradle
- **Contenedores:** Docker
- **CI/CD:** Jenkins

## Arquitectura del Proyecto
La aplicación está diseñada utilizando una arquitectura de microservicios, con los siguientes componentes principales:
- **Servicio de Autenticación y Autorización**
- **Servicio de Gestión de Usuarios**
- **Servicio de Gestión de Portafolios**
- **Servicio de Transacciones**
- **Servicio de Datos Financieros**
- **Servicio de Notificaciones**

## Instalación y Configuración
### Prerrequisitos
- Java 11 o superior
- Node.js y npm
- PostgreSQL
- Redis
- Apache Kafka
- Docker
- Jenkins

### Configuración del Entorno de Desarrollo
#### Backend
1. **Clonar el repositorio:**
    ```sh
    git clone https://github.com/LeoBellier/PortfolioManagement.git
    cd PortfolioManagement/backend
    ```

2. **Configurar PostgreSQL:**
    - Crear una base de datos llamada `portfolio_management`.
    - Configurar las credenciales de la base de datos en el archivo `application.properties`.

3. **Configurar Redis:**
    - Instalar y ejecutar Redis en tu máquina local.

4. **Configurar Kafka:**
    - Descargar e instalar Kafka.
    - Iniciar ZooKeeper y Kafka Server.
    - Crear un topic llamado `portfolio-transactions`.

5. **Configurar Docker:**
    - Crear un archivo `Dockerfile` para el backend:
    ```dockerfile
    FROM openjdk:11-jdk-slim
    VOLUME /tmp
    COPY build/libs/*.jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
    - Crear un archivo `docker-compose.yml` para orquestar los servicios:
    ```yaml
    version: '3.8'
    services:
      postgres:
        image: postgres:latest
        environment:
          POSTGRES_DB: portfolio_management
          POSTGRES_USER: postgres
          POSTGRES_PASSWORD: password
        ports:
          - "5432:5432"

      redis:
        image: redis:latest
        ports:
          - "6379:6379"

      zookeeper:
        image: wurstmeister/zookeeper:3.4.6
        ports:
          - "2181:2181"

      kafka:
        image: wurstmeister/kafka:2.12-2.2.1
        ports:
          - "9092:9092"
        environment:
          KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
          KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
          KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

      backend:
        build: .
        ports:
          - "8080:8080"
        depends_on:
          - postgres
          - redis
          - kafka
    ```

6. **Instalar dependencias y construir el proyecto:**
    ```sh
    ./gradlew clean build
    ```

7. **Ejecutar la aplicación con Docker Compose:**
    ```sh
    docker-compose up
    ```

#### Frontend
1. **Clonar el repositorio:**
    ```sh
    cd ../frontend
    ```

2. **Instalar dependencias:**
    ```sh
    npm install
    ```

3. **Configurar variables de entorno:**
    - Crear un archivo `.env` en el directorio `frontend` con la URL del backend:
    ```
    REACT_APP_BACKEND_URL=http://localhost:8080/api
    ```

4. **Ejecutar la aplicación:**
    ```sh
    npm start
    ```

## Uso de la Aplicación
### Endpoints Principales
#### Backend
- **Autenticación:**
    - `POST /api/auth/register`: Registro de nuevo usuario.
    - `POST /api/auth/login`: Inicio de sesión.
- **Gestión de Usuarios:**
    - `GET /api/users/{id}`: Obtener información del usuario.
    - `PUT /api/users/{id}`: Actualizar información del usuario.
- **Gestión de Portafolios:**
    - `POST /api/portfolios`: Crear un nuevo portafolio.
    - `GET /api/portfolios/{id}`: Obtener información de un portafolio.
    - `PUT /api/portfolios/{id}`: Actualizar un portafolio.
- **Transacciones:**
    - `POST /api/transactions`: Ejecutar una transacción (compra/venta).
- **Datos Financieros:**
    - `GET /api/stocks/{symbol}`: Obtener el precio de una acción en tiempo real.

#### Frontend
1. **Registro y Login:**
    - Páginas para registrar nuevos usuarios y para iniciar sesión.
2. **Gestión de Perfil:**
    - Página para ver y actualizar el perfil de usuario.
3. **Gestión de Portafolios:**
    - Página para crear, ver y actualizar portafolios de inversión.
4. **Transacciones:**
    - Página para ejecutar transacciones de compra y venta de acciones.
5. **Consulta de Datos Financieros:**
    - Página para consultar el precio de acciones en tiempo real.

## Pruebas
### Pruebas Unitarias
- Utilizar JUnit y Mockito para pruebas unitarias en el backend.
- Utilizar Jest para pruebas unitarias en el frontend.
### Pruebas de Integración
- Utilizar Spring Boot Test para pruebas de integración en el backend.
- Utilizar Cypress para pruebas de integración en el frontend.
### Pruebas de Carga
- Utilizar JMeter o Gatling para simular carga y medir el rendimiento del sistema.

## Integración Continua y Despliegue
### Jenkins
1. **Configurar Jenkins:**
    - Instalar Jenkins en tu máquina local o servidor.
    - Instalar los plugins necesarios (Gradle, Docker, Docker Compose).

2. **Pipeline de Jenkins:**
    - Crear un archivo `Jenkinsfile` en el repositorio:
    ```groovy
    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    script {
                        docker.image('gradle:6.8.3-jdk11').inside {
                            sh 'gradle clean build'
                        }
                    }
                }
            }

            stage('Test') {
                steps {
                    script {
                        docker.image('gradle:6.8.3-jdk11').inside {
                            sh 'gradle test'
                        }
                    }
                }
            }

            stage('Build Docker Image') {
                steps {
                    script {
                        sh 'docker build -t tu-usuario/portfolio-backend .'
                    }
                }
            }

            stage('Deploy with Docker Compose') {
                steps {
                    script {
                        sh 'docker-compose up -d'
                    }
                }
            }
        }

        post {
            always {
                cleanWs()
            }
        }
    }
    ```

### Despliegue en Docker
- Crear Dockerfiles para cada servicio.
- Usar Docker Compose para orquestar los servicios.

### Despliegue en Kubernetes
- Configurar archivos YAML para el despliegue en Kubernetes.
- Utilizar un proveedor de nube como AWS EKS, GKE o AKS para el despliegue.

## Contribuciones
Las contribuciones son bienvenidas. Por favor, abre un issue o un pull request para discutir cualquier cambio importante antes de enviar una propuesta.

## Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.
