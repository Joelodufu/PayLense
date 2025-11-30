# PayLense Security Strategy

## 1. Authentication & Authorization
- **Mechanism**: JSON Web Tokens (JWT) for stateless authentication.
- **Password Storage**: BCrypt hashing (Cost factor 10+).
- **Access Control**: Role-Based Access Control (RBAC) for User vs Admin operations.

## 2. Transaction Integrity
- **Concurrency Control**: Optimistic Locking (@Version) on Wallet entities to prevent race conditions.
- **Idempotency**: Idempotency keys required for all fund transfer endpoints to prevent double-spending.
- **ACID**: All financial operations wrapped in @Transactional.

## 3. Data Protection
- **Transport**: HTTPS/TLS 1.2+ required for all external connections.
- **Input Validation**: JSR-303/380 Bean Validation on all DTOs.
- **Sensitive Data**: PII (Personally Identifiable Information) minimized and protected.

## 4. Audit & Logging
- **Audit Trail**: Dedicated `transaction_logs` table recording every balance change.
- **Details**: Timestamp, User ID, Transaction Type, Amount, IP Address, Device ID.

## 5. Infrastructure
- **Database**: MySQL with restricted user permissions.
- **API Security**: Rate limiting (Bucket4j) and CORS configuration.
