# Project Requirements and Configurations:

## Project Logic Layered Architecture:

![Layered Architecture](https://github.com/souzafcharles/Invest-Portfolio-Microservices/blob/main/portfolio/logic-layered-architecture.png)

***

## Requirements for `application.properties` Configuration File:

- **Application Configuration:**
    - Define the application name under `spring.application.name` as `portfolio` for service identification within the
      system.

- **Server Configuration:**
    - Set the server port to `8081` using `server.port`.

- **Database Configuration:**
    - Configure the `datasource` properties for PostgreSQL:
        - `spring.datasource.url`: Specifies the JDBC URL for the PostgreSQL database as
          `jdbc:postgresql://localhost:5432/ms-portfolio`.
        - `spring.datasource.username`: Sets the database username as `postgres`.
        - `spring.datasource.password`: Sets the database password as `postgres`.

- **JPA and Hibernate Configuration:**
    - Enable automatic schema updates by setting `spring.jpa.hibernate.ddl-auto` to `update`.
    - Optimise database access for web applications by setting `spring.jpa.open-in-view` to `true`.
    - Enable SQL logging for debugging by setting `spring.jpa.show-sql` to `true`.

- **RabbitMQ Configuration:**
    - Configure the RabbitMQ server connection:
        - `spring.rabbitmq.addresses`: Specifies the CloudAMQP RabbitMQ instance address
          `amqps://lmbjkbor:***@kebnekaise.lmq.cloudamqp.com/lmbjkbor`.
    - Define the messaging components for asynchronous processing:
        - `broker.queue.process.name`: Specifies the queue name as `portfolio.process.queue`.
        - `broker.exchange.process.name`: Specifies the exchange name as `exchange.process`.

***

# Backend Requirements Specification:

## Requirements for `PortfolioApplication` Class

### Class Design

- **Purpose:** The `PortfolioApplication` class serves as the entry point for the Portfolio microservice. It is
  responsible for initialising and running the Spring Boot application.

### Methods

- **`main(String[] args)` Method:**
    - **Purpose:** Bootstraps the Spring Boot application.
    - Calls `SpringApplication.run()` with the `PortfolioApplication` class and the command-line arguments to start the
      application.

### Annotations

- **`@SpringBootApplication`:**
    - Declares the class as the entry point for the Spring Boot framework, encompassing configuration, component
      scanning, and enabling auto-configuration.

### Documentation and Conventions

- Adhere to best practices for the `main()` method implementation and ensure clear class-level documentation.

---

## Requirements for `Portfolio` Entity Class

### Entity Class Design

- **Purpose:** The `Portfolio` entity class models an investment portfolio within the Portfolio microservice, serving as
  a central domain entity.

### Attributes and Annotations

- Define the following attributes:
    - `id`: Serves as the unique identifier for the `Portfolio` entity.
        - Annotated with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` for primary key generation.
    - `title`: Represents the portfolio's name or title.
        - Annotated with `@Column` for database mapping.
    - `assets`: Represents a list of assets included in the portfolio.
        - Annotated with `@OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL)` to establish the relationship
          with the Asset class.
        - Annotated with `@JsonManagedReference` to manage serialisation and prevent cyclic references during JSON
          conversion.

### Constructors

- Provide the following constructors:
    - A no-argument constructor to comply with JavaBeans conventions.

### Accessors and Mutators

- Implement `getters` and `setters` for all attributes to provide controlled data access:
    - `public Long getId()` and `public void setId(Long id)`
    - `public String getTitle()` and `public void setTitle(String title)`
    - `public List<Asset> getAssets()` and `public void setAssets(List<Asset> assets)`

### Equals and HashCode

- **`equals()` Method:**
    - Compare `Portfolio` instances using the `id` attribute:
        - Example: `return Objects.equals(id, portfolio.id);`
- **`hashCode()` Method:**
    - Generate a hash code based on the `id` attribute:
        - Example: `return Objects.hashCode(id);`

### Documentation and Conventions

- Use standard naming conventions and include inline and Javadoc comments to enhance maintainability and clarity.

---

## Requirements for `Asset` Entity Class

### Entity Class Design

- **Purpose:** The `Asset` entity class represents individual financial assets within a portfolio, such as stocks or
  bonds.

### Attributes and Annotations

- Define the following attributes:
    - `id`: Serves as the unique identifier for the `Asset` entity.
        - Annotated with `@Id` and `@GeneratedValue(strategy = GenerationType.IDENTITY)` for primary key generation.
    - `name`: Represents the name of the financial asset (e.g., stock or bond).
    - `quantity`: Represents the number of units of the asset.
    - `portfolio`: Establishes the relationship between the asset and its parent portfolio.
        - Annotated with `@ManyToOne` to define the many-to-one relationship.
        - Annotated with `@JoinColumn(name = "portfolio_id")` to map the foreign key.
        - Annotated with `@JsonBackReference` to manage serialisation and prevent cyclic references during JSON
          conversion.

### Constructors

- Provide the following constructors:
    - A no-argument constructor to comply with JavaBeans conventions.

### Accessors and Mutators

- Implement `getters` and `setters` for all attributes to ensure controlled data access:
    - `public Long getId()` and `public void setId(Long id)`
    - `public String getName()` and `public void setName(String name)`
    - `public Integer getQuantity()` and `public void setQuantity(Integer quantity)`
    - `public Portfolio getPortfolio()` and `public void setPortfolio(Portfolio portfolio)`

### Equals and HashCode

- **`equals()` Method:**
    - Compare `Asset` instances using the `id` attribute:
        - Example: `return Objects.equals(id, asset.id);`
- **`hashCode()` Method:**
    - Generate a hash code based on the `id` attribute:
        - Example: `return Objects.hashCode(id);`

### Documentation and Conventions

- Follow Java best practices for entity implementation, and ensure that class-level and method-level documentation is
  included for maintainability.
---
## Requirements for `PortfolioRequestDTO` Class

### Class Design

- **Purpose:** The `PortfolioRequestDTO` class is a data transfer object designed to encapsulate essential portfolio details (`id` and `title`) for inter-service communication via RabbitMQ.

### Attributes

- `id`: Represents the unique identifier for the portfolio.
- `title`: Represents the title of the portfolio.

### Design

- Utilises the `record` feature in Java to simplify data representation and enforce immutability:
    - Example: `public record PortfolioRequestDTO(Long id, String title) { }`

### Documentation and Conventions

- Ensure comprehensive documentation for the fields to enhance readability and clarity.

---

## Requirements for `PortfolioRepository` Interface

### Repository Design

- **Purpose:** The `PortfolioRepository` interface provides persistence operations for the `Portfolio` entity, leveraging the functionality of Spring Data JPA.

### Methods

- Inherits CRUD operations from `JpaRepository`, such as:
    - `save(Portfolio entity)`: Persists the portfolio entity to the database.
    - `findAll()`: Retrieves all portfolio records.
    - `findById(Long id)`: Fetches a specific portfolio based on its ID.
    - `deleteById(Long id)`: Removes a portfolio from the database.

### Annotations

- **`@Repository`:**
    - Marks the interface as a Spring Data JPA repository.

---

## Requirements for `PortfolioService` Class

### Class Design

- **Purpose:** The `PortfolioService` class encapsulates the business logic for managing `Portfolio` entities, including creation and retrieval of portfolio data.

### Methods

- **`create(Portfolio entity)` Method:**
    - Associates assets with the portfolio before persisting to the database.
    - Saves the portfolio entity using the `PortfolioRepository`.
- **`readAll()` Method:**
    - Retrieves a list of all portfolios using the `PortfolioRepository`.

### Annotations

- **`@Service`:**
    - Declares the class as a service component in the Spring framework.

### Documentation and Conventions

- Document the methods clearly to describe their role in the business logic.

---

## Requirements for `PortfolioController` Class

### Class Design

- **Purpose:** The `PortfolioController` class serves as the RESTful interface for the Portfolio microservice, handling HTTP requests for portfolio management.

### Endpoints

- **`POST /portfolio`:**
    - Accepts a `Portfolio` object in the request body.
    - Invokes the `PortfolioService` to create the portfolio.
    - Converts the `Portfolio` to `PortfolioRequestDTO` and publishes it to the RabbitMQ queue.
- **`GET /portfolio`:**
    - Fetches a list of all portfolios by calling the `PortfolioService`.

### Annotations

- **`@RestController`:**
    - Designates the class as a REST controller.
- **`@RequestMapping("/portfolio")`:**
    - Sets the base URL for all endpoints in this controller.

### Dependencies

- **`PortfolioService`:** Injected via `@Autowired` to manage portfolio operations.
- **`RabbitTemplate`:** Used to send messages to RabbitMQ.
- **`@Value("${broker.queue.process.name}")`:** Injects the queue name for RabbitMQ messaging.

---

## Requirements for `RabbitMQConfig` Class

### Class Design

- **Purpose:** The `RabbitMQConfig` class centralises RabbitMQ configuration, defining the queue and message converter.

### Beans

- **Queue Bean:**
    - Defines a durable queue using the name specified in the `application.properties` file (`broker.queue.process.name`).
    - Example: `return new Queue(queue, true);`
- **Message Converter Bean:**
    - Configures the `Jackson2JsonMessageConverter` to facilitate JSON serialisation and deserialisation for messages.

### Annotations

- **`@Configuration`:**
    - Marks the class as a Spring configuration class.

### Documentation and Conventions

- Include inline comments to clarify the purpose of each bean.