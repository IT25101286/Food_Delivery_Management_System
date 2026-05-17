# FoodDelivery Admin Setup

This project is a Spring Boot app with MariaDB, Thymeleaf, Spring Security, and Maven.

## Requirements

- Java 21 or newer
- Maven 3.9 or newer
- MariaDB running locally
- Git

## Database Setup

The app expects a MariaDB database named `food_delivery_admin`.

Default connection in `src/main/resources/application.properties`:

- Host: `127.0.0.1`
- Port: `3306`
- User: `root`
- Password: `HaGoJH99**`

If you want to use a different database user or password, update `src/main/resources/application.properties` before running the app.

## Windows Setup

1. Install Java 21 and make sure `java -version` works in Command Prompt or PowerShell.
2. Install Maven and make sure `mvn -version` works.
3. Install MariaDB and start the service.
4. Create the database if it does not already exist:

   ```sql
   CREATE DATABASE food_delivery_admin;
   ```

5. Open a terminal in the project folder.
6. Run the project:

   ```bash
   mvn spring-boot:run
   ```

7. Open the app in the browser at `http://localhost:8082`.
8. Log in with the default admin credentials:

   - Email: `superadmin@food.com`
   - Password: `Admin@123`

## Mac Setup

1. Install Java 21 and verify it with `java -version`.
2. Install Maven and verify it with `mvn -version`.
3. Install MariaDB using Homebrew or the official package.
4. Start MariaDB.
5. Create the database if it does not already exist:

   ```sql
   CREATE DATABASE food_delivery_admin;
   ```

6. Open Terminal in the project folder.
7. Run the project:

   ```bash
   mvn spring-boot:run
   ```

8. Open the app in the browser at `http://localhost:8082`.
9. Log in with the default admin credentials:

   - Email: `superadmin@food.com`
   - Password: `Admin@123`

## Build Only

If you only want to check compilation:

```bash
mvn -DskipTests compile
```

## Notes

- The app seeds a default super admin on first startup.
- If MariaDB is already using a different root password, update the datasource settings first.
- The Hibernate MariaDB dialect warning is safe to ignore for now.