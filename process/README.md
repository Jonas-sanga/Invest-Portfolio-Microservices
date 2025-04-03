# Project Requirements and Configurations:

## Requirements for `application.properties` Configuration File:

- **Application Configuration:**
    - Define the application name under `spring.application.name` as `process` to uniquely identify the Process
      microservice.

- **Server Configuration:**
    - Set the server port to `8082` using `server.port`.

- **MongoDB Configuration:**
    - Configure the connection to the MongoDB database:
        - `spring.data.mongodb.authentication-database`: Specifies the authentication database as `admin`.
        - `spring.data.mongodb.auto-index-creation`: Enables automatic index creation by setting it to `true`.
        - `spring.data.mongodb.host`: Defines the MongoDB server's host as `localhost`.
        - `spring.data.mongodb.port`: Specifies the MongoDB server's port as `27017`.
        - `spring.data.mongodb.database`: Sets the name of the MongoDB database to `ms-process`.
        - `spring.data.mongodb.username`: Configures the database username as `admin`.
        - `spring.data.mongodb.password`: Configures the database password as `123`.

- **RabbitMQ Configuration:**
    - Configure the RabbitMQ server connection:
        - `spring.rabbitmq.addresses`: Specifies the CloudAMQP RabbitMQ instance address
          `amqps://lmbjkbor:***@kebnekaise.lmq.cloudamqp.com/lmbjkbor`.
    - Define the messaging components for asynchronous processing:
        - `broker.queue.process.name`: Sets the queue name as `portfolio.process.queue`.
        - `broker.exchange.process.name`: Sets the exchange name as `exchange.process`.

---

# Backend Requirements Specification:

## Requirements for `ProcessApplication` Class

### Class Design

- **Purpose:** The `ProcessApplication` class serves as the entry point for the Process microservice and is responsible
  for initialising and running the Spring Boot application.

### Methods

- **`main(String[] args)` Method:**
    - **Purpose:** Bootstraps the Spring Boot application.
    - Calls `SpringApplication.run()` with the `ProcessApplication` class and the command-line arguments to start the
      application.

### Annotations

- **`@SpringBootApplication`:**
    - Marks the class as the entry point for the Spring Boot framework, enabling configuration, component scanning, and
      auto-configuration.

### Documentation and Conventions

- Adhere to Java best practices for the implementation of the `main()` method and ensure clear class-level
  documentation.

---

## Requirements for `ProcessRequestDTO` Class

### Class Design

- **Purpose:** The `ProcessRequestDTO` class is a data transfer object designed to encapsulate essential portfolio
  attributes (`id` and `title`) for inter-service communication via RabbitMQ.

### Attributes

- `id`: Represents the unique identifier for the portfolio.
- `title`: Represents the title of the portfolio.

### Design

- Utilises the `record` feature in Java to simplify data representation and enforce immutability:
    - Example: `public record ProcessRequestDTO(Long id, String title) { }`

### Documentation and Conventions

- Document the purpose of the fields to enhance clarity and maintainability.

---

## Requirements for `ProcessConsumer` Class

### Class Design

- **Purpose:** The `ProcessConsumer` class listens to the RabbitMQ queue for incoming portfolio data and processes it
  asynchronously.

### Methods

- **`listenerProcessQueue(ProcessRequestDTO dto)` Method:**
    - **Purpose:** Consumes messages from the `portfolio.process.queue` RabbitMQ queue and extracts portfolio details.
    - Logs the title of the portfolio using `System.out.println(dto.title());`.

### Annotations

- **`@Component`:**
    - Registers the class as a Spring component to enable dependency injection.
- **`@RabbitListener(queues = "${broker.queue.process.name}")`:**
    - Configures the method to listen to the RabbitMQ queue defined by `broker.queue.process.name`.

### Dependencies

- Relies on the RabbitMQ configuration defined in the `application.properties` file.

### Documentation and Conventions

- Ensure method-level documentation describes the purpose and flow of message processing.

---

## Requirements for `RabbitMQConfig` Class

### Class Design

- **Purpose:** The `RabbitMQConfig` class centralises configuration for RabbitMQ, ensuring proper setup for messaging
  components like queues and converters.

### Beans

- **Queue Bean:**
    - Defines a durable queue using the name configured in the `application.properties` file (
      `broker.queue.process.name`).
    - Example: `return new Queue(queue, true);`
- **Message Converter Bean:**
    - Configures `Jackson2JsonMessageConverter` to seamlessly serialise and deserialise JSON messages for RabbitMQ
      communication.

### Annotations

- **`@Configuration`:**
    - Declares the class as a configuration class in the Spring framework.

### Documentation and Conventions

- Provide inline comments describing the purpose and functionality of each bean.