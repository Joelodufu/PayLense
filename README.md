## 🐳 Docker Setup

You can run the application and the database using Docker Compose.

### Prerequisites
- Docker
- Docker Compose

### Running the Application
1. Build and start the containers:
   ```bash
   docker-compose up --build
   ```
2. The application will be available at `http://localhost:8080`.
3. The MySQL database will be running on port `3306`.

### Stopping the Application
```bash
docker-compose down
```
