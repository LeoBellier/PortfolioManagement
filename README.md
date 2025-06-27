# Portfolio Management System

The **Portfolio Management System** is a cloud-native, microservices-based application designed to help users manage their financial portfolios efficiently. This system is deployed on **AWS** and leverages various modern technologies, including **Spring Boot, Kafka, PostgreSQL, Redis, and Docker**.

## System Architecture

The system follows a **microservices architecture** where different services handle specific functionalities. These services communicate via **REST APIs and Kafka for event-driven messaging**.

### High-Level System Context

```mermaid
graph TD;
  User[User] -->|Interacts with| System[Portfolio Management System]
  Admin[Administrator] -->|Manages| System

  System -->|Uses| ExternalAPI[External Market Data API]
  System -->|Deploys on| AWS[AWS Cloud]
  System -->|Authenticates with| AuthProvider[OAuth2 / Identity Provider]
  System -->|Notifies Users via| EmailService[Email Notification Service]
  System -->|Stores Data in| Database[(PostgreSQL)]
  System -->|Caches Data in| RedisCache[(Redis)]
  System -->|Processes Events with| Kafka[(Kafka Broker)]
  System -->|Stores Reports in| S3[(AWS S3)]
```

## Communication Between Services

- **API Gateway**: Acts as the entry point for users and routes requests to the appropriate microservices.
- **Service Discovery (Eureka)**: Enables dynamic service registration and discovery.
- **Synchronous Communication**: RESTful APIs are used for direct service-to-service interaction.
- **Asynchronous Communication**: Kafka is used for event-driven messaging to decouple services and improve scalability.
- **Database & Caching**: PostgreSQL serves as the primary data store, while Redis is used for caching frequently accessed data.

## Deployment

The system is containerized using **Docker** and orchestrated using **Kubernetes on AWS (EKS)**. Other AWS services such as **S3** (for storage) and **SNS/SQS** (for notifications) are also integrated.

## Future Enhancements

- Implementing GraphQL for optimized data fetching.
- Enhancing monitoring with Prometheus & Grafana.
- Expanding event-driven capabilities with additional Kafka topics.

---
This README provides a **high-level overview** of the system. For detailed documentation, refer to the individual microservice documentation.
