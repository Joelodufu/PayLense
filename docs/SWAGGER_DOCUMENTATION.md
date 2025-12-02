# PayLense API Swagger Documentation

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/Joelodufu/PayLense.git
   cd PayLense
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Configure environment variables:
   - Create a `.env` file in the root directory.
   - Add necessary environment variables such as database connection strings, API keys, etc.

4. Run the development server:
   ```bash
   npm start
   ```

5. Access the API documentation locally at:
   ```
   http://localhost:3000/api-docs
   ```

---

## API Endpoints

### Authentication

#### POST /auth/login
- Description: Authenticate user and return a JWT token.
- Request Body:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
- Responses:
  - 200: Successful login with token.
  - 401: Unauthorized.

#### POST /auth/register
- Description: Register a new user.
- Request Body:
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- Responses:
  - 201: User created.
  - 400: Bad request.

---

### Payments

#### GET /payments
- Description: Retrieve list of payments.
- Query Parameters:
  - `page` (integer, optional): Page number for pagination.
  - `limit` (integer, optional): Number of items per page.
- Responses:
  - 200: List of payments.

#### POST /payments
- Description: Create a new payment.
- Request Body:
  ```json
  {
    "amount": 100.00,
    "currency": "USD",
    "method": "credit_card",
    "description": "Payment for order #1234"
  }
  ```
- Responses:
  - 201: Payment created.
  - 400: Bad request.

---

### Usage Examples

#### Example: Login
```bash
curl -X POST http://localhost:3000/auth/login \
-H "Content-Type: application/json" \
-d '{"email":"user@example.com","password":"password123"}'
```

#### Example: Create Payment
```bash
curl -X POST http://localhost:3000/payments \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <token>" \
-d '{"amount":100.00,"currency":"USD","method":"credit_card","description":"Payment for order #1234"}'
```

---

For more detailed API specs, please refer to the Swagger UI or contact the development team.