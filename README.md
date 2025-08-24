# Inventory App (Java 17, Spring Boot, MySQL)

This is a starter inventory management REST API using Java 17, Spring Boot, JPA (MySQL), and JWT-based authentication.

Features:
- User signup and login (returns JWT)
- Roles: ROLE_USER, ROLE_ADMIN (seeded admin/admin123 and user/user123)
- Product and Category entities (seeded sample products)
- AI endpoint `/api/ai/recommendations` — rule-based reorder suggestions (low-stock)
- CommandLineRunner seeds categories and products on first run

How to run locally (IntelliJ + MySQL):
1. Ensure MySQL is running and create a database `inventory_db` or let the app create it (configured in `application.yml`).
2. Update `src/main/resources/application.yml` with your MySQL username/password.
3. Import the project into IntelliJ as a Maven project.
4. Run `mvn spring-boot:run` or run `InventoryApplication.java`.
5. API:
   - POST /api/auth/signup  { "username":"new","password":"pwd" }
   - POST /api/auth/login   { "username":"user","password":"user123" }
   - Use `Authorization: Bearer <token>` for protected endpoints.
   - GET /api/products
   - GET /api/ai/recommendations?threshold=25

Notes:
- JWT secret in application.yml is for demo only — change for production.
- There is a placeholder for OpenAI integration in application.yml; currently the AI endpoint is rule-based.
