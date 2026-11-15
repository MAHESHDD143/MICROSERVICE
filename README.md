# Microservices Demo: Product, Order and Inventory Services

A Spring Boot microservices project made up of three independent services behind a single API Gateway. Each service owns its own database and can be built, run, and deployed on its own.

## Services

| Service | Port | Database | Responsibility |
| --- | --- | --- | --- |
| **api-gateway** | 9000 | none | Single entry point that routes requests to the other services |
| **product-service** | 8080 | MongoDB | Create and list products |
| **order-service** | 8081 | MySQL (`order_service`) | Place and store orders |
| **inventory-service** | 8082 | MySQL (`inventory_service`) | Check whether a SKU is in stock |

## Architecture

```
                         Client
                            |
                            v
                  API Gateway  (:9000)
          /api/product  /api/order  /api/inventory
              |             |              |
              v             v              v
      product-service  order-service  inventory-service
          (:8080)         (:8081)          (:8082)
              |             |              |
           MongoDB        MySQL          MySQL
                       order_service  inventory_service
```

Clients only talk to the gateway on port 9000. The gateway forwards each request to the matching service based on the URL path.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Cloud Gateway (Server WebMVC)
- Spring Data MongoDB and Spring Data JPA
- MongoDB 7 and MySQL 8
- Flyway for database migrations (order and inventory services)
- Docker Compose for local databases
- Testcontainers and REST Assured for integration tests
- Maven, Lombok

## Project Structure

```
.
├── api-gateway/          # Route definitions (Routes.java)
├── product-service/      # Controller, service, repository, MongoDB document
├── order-service/        # Controller, service, repository, JPA entity, Flyway migration
└── inventory-service/    # Controller, service, repository, JPA entity, Flyway migrations
```

Each service follows the same layered structure: `controller`, `service`, `repository`, `entity`, and `dto` packages.

## Prerequisites

- Java 21
- Maven (or the included `mvnw` wrapper)
- Docker and Docker Compose

## Getting Started

### 1. Start the databases

MongoDB for the product service:

```bash
cd product-service
docker compose up -d
```

MySQL for the order and inventory services. The `init.sql` in `inventory-service` creates both `order_service` and `inventory_service` databases:

```bash
cd inventory-service
docker compose up -d
```

MySQL must be reachable on `localhost:3306`, which is what the services' `application.properties` files use. Flyway creates the tables and loads sample stock data when each service starts.

### 2. Start the services

Open a separate terminal for each service:

```bash
cd product-service   && ./mvnw spring-boot:run
cd order-service     && ./mvnw spring-boot:run
cd inventory-service && ./mvnw spring-boot:run
cd api-gateway       && ./mvnw spring-boot:run
```

### 3. Try it through the gateway

Create a product:

```bash
curl -X POST http://localhost:9000/api/product \
  -H "Content-Type: application/json" \
  -d '{"name":"iPhone 15","description":"Apple smartphone","price":999.99}'
```

List products:

```bash
curl http://localhost:9000/api/product
```

Check stock:

```bash
curl "http://localhost:9000/api/inventory?skuCode=iphone_15&quantity=2"
```

Place an order:

```bash
curl -X POST http://localhost:9000/api/order \
  -H "Content-Type: application/json" \
  -d '{"skuCode":"iphone_15","price":999.99,"quantity":2}'
```

## API Reference

All endpoints are called through the gateway at `http://localhost:9000`.

| Method | Endpoint | Description | Request |
| --- | --- | --- | --- |
| POST | `/api/product` | Create a product | JSON body: `name`, `description`, `price` |
| GET | `/api/product` | List all products | none |
| POST | `/api/order` | Place an order | JSON body: `skuCode`, `price`, `quantity` |
| GET | `/api/inventory` | Check if stock is available | Query params: `skuCode`, `quantity` (returns `true` or `false`) |

## Sample Inventory Data

Flyway loads this stock on first run (100 units each):

`iphone_15`, `pixel_8`, `galaxy_24`, `oneplus_12`

## Running the Tests

Integration tests use Testcontainers, so Docker must be running:

```bash
cd <service-folder>
./mvnw test
```

## Roadmap

- Call inventory-service from order-service to validate stock before saving an order
- Service discovery so routes are not hard-coded to localhost ports
- Authentication and authorization at the gateway
- Centralized configuration and externalized secrets
- Circuit breaker and retry for inter-service calls
- Dockerfiles and a single Docker Compose file to run everything together
- Input validation and global exception handling

## Author

**Donga Mahesh**
Java Backend Developer
[LinkedIn](https://www.linkedin.com/in/mahesh-donga-a320b6256) | [GitHub](https://github.com/MAHESHD13)
