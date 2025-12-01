# PayLense API Documentation

## Overview
PayLense is a payment processing API that allows developers to integrate payment functionalities into their applications. This documentation provides an overview of the API, setup instructions, endpoint descriptions, sample data usage, and how to access the Swagger UI for interactive API exploration.

---

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
   Create a `.env` file in the root directory and add necessary configuration such as database connection strings, API keys, etc.

4. Run the application:
   ```bash
   npm start
   ```

---

## Swagger UI Access

The API documentation is available interactively via Swagger UI.

- After running the application, open your browser and navigate to:
  ```
  http://localhost:3000/api-docs
  ```
- This interface allows you to explore all available endpoints, see request/response schemas, and test API calls directly.

---

## API Endpoints

### 1. Create Payment
- **Endpoint:** `POST /payments`
- **Description:** Create a new payment transaction.
- **Request Body Example:**
  ```json
  {
    "amount": 100.00,
    "currency": "USD",
    "paymentMethod": "credit_card",
    "description": "Payment for order #1234"
  }
  ```
- **Response Example:**
  ```json
  {
    "id": "pay_abc123",
    "status": "pending",
    "amount": 100.00,
    "currency": "USD",
    "createdAt": "2024-06-01T12:00:00Z"
  }
  ```

### 2. Get Payment Details
- **Endpoint:** `GET /payments/{id}`
- **Description:** Retrieve details of a specific payment by ID.
- **Response Example:**
  ```json
  {
    "id": "pay_abc123",
    "status": "completed",
    "amount": 100.00,
    "currency": "USD",
    "paymentMethod": "credit_card",
    "description": "Payment for order #1234",
    "createdAt": "2024-06-01T12:00:00Z",
    "completedAt": "2024-06-01T12:05:00Z"
  }
  ```

### 3. List Payments
- **Endpoint:** `GET /payments`
- **Description:** List all payments with optional filters.
- **Query Parameters:**
  - `status` (optional): Filter by payment status (e.g., `pending`, `completed`).
  - `limit` (optional): Number of records to return.
- **Response Example:**
  ```json
  [
    {
      "id": "pay_abc123",
      "status": "completed",
      "amount": 100.00,
      "currency": "USD"
    },
    {
      "id": "pay_def456",
      "status": "pending",
      "amount": 50.00,
      "currency": "USD"
    }
  ]
  ```

---

## Sample Data Usage

You can use the following sample data to test the API endpoints:

```json
{
  "amount": 150.00,
  "currency": "USD",
  "paymentMethod": "paypal",
  "description": "Sample payment for testing"
}
```

Use this data in the `POST /payments` endpoint to create a new payment.

---

For more detailed information, please refer to the Swagger UI or the source code in the repository.

---

Thank you for using PayLense!