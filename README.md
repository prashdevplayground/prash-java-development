# Inventory gRPC App (Spring Boot + MySQL + JWT)

End-to-end inventory management sample with REST + gRPC APIs, MySQL persistence, role-based auth, and a simple AI reorder recommendation.

## Features
- Java 17, Spring Boot 3
- REST controllers and gRPC service (`ProductServiceGrpc`)
- MySQL + Spring Data JPA
- JWT-based signup/login with roles (ROLE_USER, ROLE_ADMIN)
- Products with categories (camera, thermostats), seeded via `data.sql`
- Inventory transactions
- Simple AI feature: reorder recommendation via exponential moving average
- Config via `application.yml` (HTTP 8080, gRPC 9090)

## Getting Started (Local IntelliJ + MySQL)
1. Ensure MySQL is running and create a user that matches `spring.datasource.*` in `application.yml` (default: root/password).
2. `mvn clean spring-boot:run` or run `InventoryApplication` from IntelliJ.
3. REST endpoints (use a tool like curl/Postman):
   - `POST /api/auth/signup` `{ "username":"admin", "password":"secret", "admin":true }`
   - `POST /api/auth/login` -> returns JWT
   - Use `Authorization: Bearer <token>` for subsequent requests.
   - `GET /api/products` (list)
   - `GET /api/products/search?q=camera`
   - `GET /api/products/{id}/ai/reorder` (AI suggestion)
   - `POST /api/inventory/apply` `{ "productId": 1, "delta": -2 }` (sale)
4. gRPC: connect to `localhost:9090` using the generated stubs from `src/main/proto/inventory.proto`.

## Notes
- For production, set `JWT_SECRET` env var.
- JPA `ddl-auto=update` creates tables automatically; `data.sql` seeds categories/products.
