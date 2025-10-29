# Forum Application - Deployment Guide

## Prerequisites

Before deploying the application, ensure you have the following installed:

1. **Java Development Kit (JDK) 8** or higher
2. **MySQL Server 5.7** or higher
3. **Apache Tomcat 8.5** or higher (or any Java EE compatible application server)
4. **Maven 3.6** or higher (for building from source)

## Database Setup

### Step 1: Create Databases

Connect to MySQL and create the production and test databases:

```sql
CREATE DATABASE forum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE DATABASE forum_db_test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Step 2: Create Database User (Optional but Recommended)

```sql
CREATE USER 'forum_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON forum_db.* TO 'forum_user'@'localhost';
GRANT ALL PRIVILEGES ON forum_db_test.* TO 'forum_user'@'localhost';
FLUSH PRIVILEGES;
```

### Step 3: Initialize Schema

Run the schema script to create the tables:

```bash
mysql -u root -p forum_db < src/main/resources/schema.sql
mysql -u root -p forum_db_test < src/main/resources/schema.sql
```

Or connect to MySQL and run:

```sql
USE forum_db;
SOURCE /path/to/ITA-4-semana/src/main/resources/schema.sql;
```

### Step 4: Update Database Configuration

Edit `src/main/resources/db.properties` with your database credentials:

```properties
db.url=jdbc:mysql://localhost:3306/forum_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=forum_user
db.password=secure_password
db.driver=com.mysql.cj.jdbc.Driver
```

Edit `src/test/resources/db.properties` for test database:

```properties
db.url=jdbc:mysql://localhost:3306/forum_db_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=forum_user
db.password=secure_password
db.driver=com.mysql.cj.jdbc.Driver
```

## Building the Application

### Using Maven

Navigate to the project directory and run:

```bash
cd ITA-4-semana
mvn clean package
```

This will:
- Compile all Java source files
- Run all unit tests (unless skipped with `-DskipTests`)
- Create a WAR file in the `target` directory: `forum.war`

### Skip Tests During Build

If you want to build without running tests:

```bash
mvn clean package -DskipTests
```

## Deployment to Tomcat

### Option 1: Using Tomcat Manager (Recommended)

1. Start Tomcat:
   ```bash
   $CATALINA_HOME/bin/startup.sh   # Linux/Mac
   %CATALINA_HOME%\bin\startup.bat  # Windows
   ```

2. Access Tomcat Manager at: `http://localhost:8080/manager`

3. Login with admin credentials (configured in `tomcat-users.xml`)

4. Under "WAR file to deploy", select `target/forum.war` and click "Deploy"

### Option 2: Manual Deployment

1. Copy the WAR file to Tomcat's webapps directory:
   ```bash
   cp target/forum.war $CATALINA_HOME/webapps/
   ```

2. Start Tomcat (if not already running):
   ```bash
   $CATALINA_HOME/bin/startup.sh
   ```

3. Tomcat will automatically extract and deploy the application

### Option 3: Exploded Deployment

1. Extract the WAR file:
   ```bash
   mkdir $CATALINA_HOME/webapps/forum
   cd $CATALINA_HOME/webapps/forum
   jar -xvf /path/to/forum.war
   ```

2. Start or restart Tomcat

## Accessing the Application

Once deployed, access the application at:

```
http://localhost:8080/forum
```

The default context path is `/forum` based on the WAR file name. You can change this by renaming the WAR file.

## Verification

### 1. Check Deployment

- Open `http://localhost:8080/forum` in a web browser
- You should see the login page

### 2. Test User Registration

1. Click "Register here"
2. Fill in the registration form
3. Submit and verify redirection to login page

### 3. Test Login

1. Enter credentials from registration
2. Click "Login"
3. Verify redirection to topics page

## Running Tests

### Unit Tests (DBUnit)

Make sure MySQL is running with test database configured, then:

```bash
mvn test -Dtest=*DAOTest
```

This runs all DAO tests with DBUnit.

### Selenium Tests

1. Install ChromeDriver:
   - Download from: https://chromedriver.chromium.org/
   - Add to system PATH

2. Deploy the application to Tomcat

3. Run Selenium tests:
   ```bash
   mvn test -Dtest=ForumSeleniumTest
   ```

### Run All Tests

```bash
mvn test
```

## Troubleshooting

### Database Connection Issues

**Problem**: "Cannot connect to database"

**Solution**:
- Verify MySQL is running: `sudo systemctl status mysql`
- Check credentials in `db.properties`
- Verify database exists: `SHOW DATABASES;`
- Check firewall rules for port 3306

### ClassNotFoundException

**Problem**: "java.lang.ClassNotFoundException: com.mysql.cj.jdbc.Driver"

**Solution**:
- Ensure MySQL connector JAR is in `WEB-INF/lib`
- Rebuild the WAR file: `mvn clean package`

### 404 Error

**Problem**: "HTTP Status 404 – Not Found"

**Solution**:
- Check if WAR is deployed: Look in `$CATALINA_HOME/webapps/`
- Check Tomcat logs: `$CATALINA_HOME/logs/catalina.out`
- Verify URL includes context path: `/forum`

### Port Already in Use

**Problem**: "Address already in use: 8080"

**Solution**:
- Check if another process is using port 8080:
  ```bash
  lsof -i :8080        # Linux/Mac
  netstat -ano | findstr :8080  # Windows
  ```
- Stop the conflicting process or change Tomcat's port in `server.xml`

### Session Timeout

**Problem**: Users getting logged out frequently

**Solution**:
- Increase session timeout in `web.xml`:
  ```xml
  <session-config>
      <session-timeout>60</session-timeout>  <!-- 60 minutes -->
  </session-config>
  ```

## Production Deployment Considerations

### Security

1. **Change Default Passwords**:
   - Update database password
   - Use strong passwords

2. **Enable HTTPS**:
   - Configure SSL/TLS in Tomcat
   - Update URLs to use `https://`

3. **Database Security**:
   - Don't use root account
   - Use dedicated database user with minimal privileges
   - Enable SSL for database connections

4. **Password Hashing**:
   - Current implementation stores passwords in plain text
   - **Important**: Implement password hashing (BCrypt, PBKDF2) for production

### Performance

1. **Connection Pooling**:
   - Implement JDBC connection pooling (Apache Commons DBCP or HikariCP)
   - Current implementation creates new connections per request

2. **Database Optimization**:
   - Add appropriate indexes (already included in schema)
   - Monitor slow queries
   - Configure MySQL for production workload

3. **Caching**:
   - Consider caching frequently accessed data
   - Use session caching for user data

### Monitoring

1. **Logging**:
   - Configure proper logging framework (Log4j, SLF4J)
   - Monitor application and Tomcat logs
   - Set up log rotation

2. **Database Monitoring**:
   - Monitor connection pool usage
   - Track slow queries
   - Monitor database size and growth

## File Locations

- **Application WAR**: `target/forum.war`
- **Source Code**: `src/main/java/`
- **Web Resources**: `src/main/webapp/`
- **Database Schema**: `src/main/resources/schema.sql`
- **Configuration**: `src/main/resources/db.properties`
- **Tests**: `src/test/java/`

## Support and Documentation

For detailed documentation, see:
- `README.md` - Project overview and features
- `src/main/resources/schema.sql` - Database structure
- Source code comments for implementation details

## License

MIT License - See LICENSE file for details
