# Java API Automation Framework

A scalable and maintainable API automation framework built with Java, Maven, RestAssured, and TestNG.

This project is designed to provide a reusable foundation for API testing, following modular architecture and clean code principles.

## Table of Contents

- [Overview](#overview)
- [Tech Stack](#tech-stack)
- [Project Architecture](#project-architecture)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Framework Components](#framework-components)
- [Test Coverage](#test-coverage)
- [Logging](#logging)
- [Development Roadmap](#development-roadmap)

## Overview

The purpose of this project is to build an enterprise-oriented API automation framework that supports:

- Modular Maven architecture.
- Reusable API client and service layers.
- Centralized environment configuration.
- Dynamic HTTP headers and authentication.
- Reusable request and response specifications.
- Request and response logging.
- Configurable API timeout.
- Positive and negative API testing.
- Integration-ready structure for CI/CD pipelines.

The framework currently uses [DummyJSON](https://dummyjson.com/) as its sample API.

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Maven | Build and dependency management |
| RestAssured | API automation |
| TestNG | Test execution and test lifecycle |
| Jackson | JSON serialization and deserialization |
| AssertJ | Fluent assertions |
| SLF4J | Logging abstraction |
| Logback | Logging implementation |

## Project Architecture

This project uses a Maven multi-module architecture to separate framework components and API test domains.

```text
api-automation-base/
│
├── pom.xml
│
├── api-core/
│   └── src/
│       └── main/
│           ├── java/com/qa/core/
│           │   ├── client/
│           │   ├── config/
│           │   ├── exceptions/
│           │   ├── service/
│           │   └── specification/
│           │
│           └── resources/
│               └── logback.xml
│
├── api-auth/
│   └── src/main/java/
│
├── api-user/
│   └── src/
│       ├── main/java/com/qa/user/
│       │   └── services/
│       │
│       └── test/java/com/qa/user/
│           └── tests/
│               └── UserTest.java
│
├── api-product/
│   └── src/
│
├── config/
│   ├── dev.properties
│   ├── staging.properties
│   └── prod.properties
│
├── testdata/
│
└── .gitignore
```

### Module Responsibilities

| Module | Responsibility |
|---|---|
| `api-core` | Shared framework components, configuration, API client, specifications, and logging |
| `api-auth` | Authentication-related services and functionality |
| `api-user` | User API services and automated tests |
| `api-product` | Product API services and automated tests |

## Prerequisites

Make sure the following tools are installed:

- Java Development Kit (JDK) 21 or later compatible with the project configuration.
- Apache Maven.
- Git.
- IntelliJ IDEA (recommended).

Verify your Java installation:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

## Getting Started

### 1. Clone the repository

```bash
git clone <repository-url>
```

### 2. Navigate to the project

```bash
cd api-automation-base
```

### 3. Configure the environment

Open `config/dev.properties` and configure:

```properties
base.url=https://dummyjson.com
api.timeout=30000
```

### 4. Build the project

```bash
mvn clean install
```

## Configuration

The framework uses centralized configuration management through `ConfigManager`.

Configuration values are resolved using the following priority:

1. Java System Properties.
2. Environment Variables.
3. Properties Files.

### Configuration Properties

| Property | Environment Variable | Description | Default |
|---|---|---|---|
| `base.url` | `BASE_URL` | API base URL | `https://dummyjson.com` |
| `api.timeout` | `API_TIMEOUT` | API connection/socket timeout in milliseconds | `30000` |
| `auth.token` | `AUTH_TOKEN` | Authentication token | Empty |

### Environment Configuration

The framework supports multiple environments:

```text
config/
├── dev.properties
├── staging.properties
└── prod.properties
```

Select the environment using:

```bash
-Denv=dev
```

### Override Configuration

Override the API base URL:

```bash
mvn test -Dbase.url=https://dummyjson.com
```

Override the API timeout:

```bash
mvn test -Dapi.timeout=5000
```

Use environment variables:

```bash
API_TIMEOUT=10000 mvn test
```

**Note:** Do not commit real credentials, authentication tokens, or sensitive production configuration into the repository.

## Running Tests

### Run all modules

```bash
mvn clean test
```

### Run a specific module

Run User API tests and required dependencies:

```bash
mvn clean test -pl api-user -am
```

Run Product API tests:

```bash
mvn clean test -pl api-product -am
```

### Run tests against a specific environment

```bash
mvn clean test -pl api-user -am -Denv=staging
```

### Run tests with custom timeout

```bash
mvn clean test -pl api-user -am -Dapi.timeout=5000
```

### Test execution results

TestNG and Maven Surefire generate test execution reports under:

```text
api-user/target/surefire-reports/
```

## Framework Components

### 1. ConfigManager

Responsible for centralized configuration management.

Features:

- Loads environment-specific properties.
- Supports Java System Properties.
- Supports environment variables.
- Provides default configuration values.
- Supports String, Integer, and Boolean configuration types.

### 2. HeaderManager

Responsible for managing default HTTP headers.

Features:

- Accept header.
- Content-Type header.
- Correlation ID.
- Optional Bearer authentication token.

### 3. RequestSpecFactory

Creates reusable RestAssured request specifications.

Features:

- Centralized base URI.
- Default HTTP headers.
- HTTP client configuration.
- Request and response logging when validation fails.

### 4. ResponseSpecFactory

Provides reusable response validation specifications.

Supported validations:

- Expected HTTP status code.
- Expected content type.
- Maximum response time.

### 5. ApiClient

Provides a centralized HTTP client abstraction.

Supported HTTP methods:

- GET
- POST
- PUT
- PATCH
- DELETE

Additional features:

- Request execution logging.
- Response status logging.
- Execution duration tracking.
- Custom framework exception handling.

### 6. BaseService

Provides reusable API operations for service classes.

API service classes extend `BaseService` to access common HTTP methods without duplicating client initialization and request handling.

### 7. UserService

Provides User API operations:

| Method | Endpoint | Description |
|---|---|---|
| GET | `/users` | Retrieve all users |
| GET | `/users/{id}` | Retrieve user by ID |
| POST | `/users/add` | Create a user |
| PUT | `/users/{id}` | Update a user |
| DELETE | `/users/{id}` | Delete a user |

## Test Coverage

Current User API test scenarios:

| Test Scenario | Expected Status | Type |
|---|---|---|
| Retrieve all users | 200 | Positive |
| Retrieve user by ID | 200 | Positive |
| Retrieve user with invalid ID | 404 | Negative |

The framework is designed to expand with additional test coverage, including:

- Positive scenarios.
- Negative scenarios.
- Boundary value testing.
- Edge case testing.
- Data-driven testing.
- JSON Schema validation.

## Logging

The framework uses SLF4J and Logback.

Logback configuration:

```text
api-core/src/main/resources/logback.xml
```

Example execution logs:

```text
INFO  ApiClient - Executing API request | Method: GET | Endpoint: /users

INFO  ApiClient - API response received |
Method: GET |
Endpoint: /users |
Status: 200 |
Duration: 509 ms
```

Sensitive information such as authentication tokens and passwords should never be written to logs.

## Development Roadmap

The framework will evolve through the following development phases:

- [x] Phase 1 - Basic API Automation Framework
- [x] Phase 2 - Advanced Core Framework
- [ ] Phase 3 - API Automation & Data Modeling
- [ ] Phase 4 - Test Data Management
- [ ] Phase 5 - JSON Schema Validation
- [ ] Phase 6 - Advanced TestNG Execution
- [ ] Phase 7 - Reporting Integration
- [ ] Phase 8 - CI/CD Integration with Jenkins
- [ ] Phase 9 - Secrets Management with HashiCorp Vault
- [ ] Phase 10 - Parallel Execution and Framework Optimization

## Contributing

Contributions, improvements, and suggestions are welcome.

When contributing:

1. Follow the existing project architecture.
2. Keep shared framework components inside `api-core`.
3. Avoid duplicating common API operations.
4. Write meaningful test scenarios.
5. Ensure tests pass before creating a pull request.
6. Use descriptive commit messages following Conventional Commits.

Example:

```bash
git commit -m "feat(api-user): add user creation test scenarios"
```

## License

This project is currently intended for learning and development purposes. Add an appropriate license before distributing or using it as an open-source project.