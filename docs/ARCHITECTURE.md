# Clean Architecture Layers

This document outlines the Clean Architecture layers used in the PayLense project.

## 1. Domain
- Entities: Core business objects and data structures.
- Business Logic: Rules and policies that govern the behavior of the entities.

## 2. Application
- Use Cases: Application-specific business rules and workflows.
- Interfaces: Abstractions for communication between layers.

## 3. Infrastructure
- Database: MySQL is used for data persistence.
- External Services: Integration with third-party services and APIs.

## 4. Web
- Controllers: Handle HTTP requests and responses.
- API: RESTful endpoints exposed to clients.

## Technologies
- Spring Boot: Framework used for building the application.
- MySQL: Database management system used for persistence.
