![GitHub language count](https://img.shields.io/github/languages/count/souzafcharles/Invest-Portfolio-Microservices)
![GitHub top language](https://img.shields.io/github/languages/top/souzafcharles/Invest-Portfolio-Microservices)
![GitHub](https://img.shields.io/github/license/souzafcharles/Invest-Portfolio-Microservices)
![GitHub last commit](https://img.shields.io/github/last-commit/souzafcharles/Invest-Portfolio-Microservices)

# Demonstration of Microservices with RabbitMQ (Includes CloudAMQP) managing investment portfolios in the financial sector

## System Overview:

<p align="justify">
This report presents the development of a distributed system designed to manage investment portfolios in the financial industry. The system is comprised of two independent microservices—Portfolio and Process—developed using the <code>Java Spring</code> framework. It facilitates the creation, organisation, and processing of portfolios, ensuring scalability and modularity through event-driven communication powered by <code>RabbitMQ</code>. By leveraging key technologies, such as <code>Spring Boot</code>, <code>Spring Data JPA</code>, and <code>MongoDB</code>, the architecture ensures robustness, efficiency, and flexibility for financial operations.
</p>

<p align="justify">
The Portfolio microservice serves as the backbone of the application, handling the creation and persistence of portfolio entities in a <code>PostgreSQL</code> database. Each portfolio, represented by the Portfolio class, consists of a title and an associated list of assets. These assets, defined by the Asset class, contain details such as name and quantity, capturing the composition of the investment portfolio with precision. This microservice exposes RESTful endpoints for portfolio management and incorporates message brokering capabilities. Upon portfolio creation, it serialises relevant data into JSON format and publishes it to the messaging queue configured in <code>RabbitMQ</code>, utilising the <code>Jackson2JsonMessageConverter</code>.
</p>

<p align="justify">
The Process microservice complements the Portfolio microservice by consuming messages asynchronously from the RabbitMQ queue. It listens for incoming portfolio data, deserialises it into <code>PortfolioRequestDTO</code>, and processes the information as required. The architecture ensures loose coupling between the microservices, enhancing scalability and maintainability. The Process microservice is equipped with a <code>MongoDB</code> database connection, supporting further operations or storage needs.
</p>

<p align="justify">
This distributed system harnesses the advantages of the <code>Java Spring</code> framework and event-driven design to offer an efficient solution for managing investment portfolios. With its modular architecture, the system is built to ensure high reliability, extensibility, and seamless integration with financial systems. The inclusion of PostgreSQL for persistent portfolio storage and MongoDB for dynamic processing demonstrates the strategic choice of technologies for scalability and performance. The RabbitMQ messaging broker serves as the linchpin for communication between microservices, supporting asynchronous processing and enhancing overall system reliability.
</p>

***
## Project Microservice Architecture:

![Microservice Architecture](https://github.com/souzafcharles/Invest-Portfolio-Microservices/blob/main/Microservice-Architecture.png)

The architecture comprises the following key components:

* **Client Apps:**
    * Represents client devices (e.g., desktops, mobile phones) that interact with the system via the Portfolio API.

* [Portfolio Microservice:](https://github.com/souzafcharles/Invest-Portfolio-Microservices/tree/main/portfolio)
    * Provides RESTful APIs for creating, retrieving, and managing investment portfolios;
    * Stores portfolio and asset data in a PostgreSQL database;
    * Publishes portfolio creation events to RabbitMQ for asynchronous processing;
    * Operates on port 8081.

* **RabbitMQ (Broker):**
    * Serves as the messaging broker for communication between microservices;
    * Includes an exchange (`exchange.process`) to route messages to the appropriate queue;
    * Manages a queue (`portfolio.process.queue`) to hold messages for processing.

* [Process Microservice:](https://github.com/souzafcharles/Invest-Portfolio-Microservices/tree/main/process)
    * Consumes messages from the RabbitMQ queue asynchronously;
    * Deserialises messages into `PortfolioRequestDTO` for processing;
    * Operates on port 8082;
    * Connects to MongoDB for dynamic storage and further operations.

* **Persistence and Messaging:**
    * Utilises PostgreSQL for storing persistent portfolio data in the Portfolio microservice;
    * Utilises MongoDB for dynamic storage in the Process microservice;
    * RabbitMQ enables decoupled, event-driven communication between the Portfolio and Process microservices.

* **Technology Stack:**
    * Employs the <code>Java Spring</code> framework, incorporating <code>Spring Boot</code>, <code>Spring Data JPA</code>, and RabbitMQ integration;
    * Utilises <code>Jackson2JsonMessageConverter</code> to serialise and deserialise JSON messages seamlessly.


## Project Stack:

| Technology        | Version    | Description                                                                      |
|-------------------|------------|----------------------------------------------------------------------------------|
| 📐 IntelliJ IDEA  | `2024.3`   | Integrated Development Environment (IDE)                                         |
| ☕ Java           | `21`       | Backend programming language                                                     |
| 🌱 Spring Boot    | `3.4.4`    | Framework for creating Spring applications                                       |
| 🐦 Maven          | `3.9.9`    | Build automation and dependency management tool                                  |
| 🐘 PostgreSQL     | `15.4`     | Open-source relational database management system used in Portfolio Microservice |
| 🍃 MongoDB        | `8.0`      | NoSQL document-oriented database used in Process Microservice                    |
| 🐇 RabbitMQ       | `3.12.1`   | Open-source message broker used for asynchronous communication                   |
| ☁️ CloudAMQP      | `4.0`      | Hosted RabbitMQ service (via CloudAMQP) used for asynchronous message brokering  |
| 👩‍🚀 Postman        | `11.19`    | API testing and development tool                                                 |

***

## Dependencies:

| Dependency                           | Category         | Description                                                                                 |
|--------------------------------------|------------------|------------------------------------------------------------------------------------------ --|
| 🌐 Spring Web                        | Web              | Builds web applications, including RESTful APIs using Spring MVC                            |
| 💾 Spring Data JPA                   | SQL              | Simplifies database interactions using JPA with Spring Data and Hibernate                   |
| 🐘 PostgreSQL Driver                 | SQL              | Provides connectivity between Java applications and PostgreSQL databases                    |
| 🍃 Spring Data MongoDB               | NoSQL            | Integrates with MongoDB, enabling reactive as well as blocking data access                  |
| 🐇 Spring AMQP                       | Messaging        | Provides integration with RabbitMQ for message-driven architecture; utilised with CloudAMQP |
| 🌱 Spring Boot Devtools              | Developer Tools  | Offers development-time tools for application restart and debugging                         |
