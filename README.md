# PayLense

PayLense is a robust payment gateway backend built with Java (Spring Boot) and MySQL, following Clean Architecture principles.

## Features

- **User Authentication**: JWT-based authentication with phone number as wallet ID.
- **Wallet Management**: Automatic wallet creation, balance tracking (NGN).
- **Transactions**: Send/Receive money, atomic transfers, optimistic locking for concurrency control.
- **Account Management**: User profile updates, account settings, and balance inquiries.

## Tech Stack

- **Language**: Java 17+
- **Framework**: Spring Boot 3.x
- **Database**: MySQL
- **Security**: Spring Security, JWT
- **Architecture**: Clean Architecture

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and receive JWT

### Transactions
- `POST /api/transactions/transfer` - Transfer funds between wallets
- `GET /api/transactions/history` - View transaction history

### Account Management
- `GET /api/account/profile` - Get user profile
- `PUT /api/account/profile` - Update user profile
- `GET /api/account/settings` - Get account settings
- `PUT /api/account/settings` - Update account settings
- `GET /api/account/balance` - Get current wallet balance

## Getting Started

1. Clone the repository.
2. Configure MySQL database in `application.properties`.
3. Run `mvn spring-boot:run`.
