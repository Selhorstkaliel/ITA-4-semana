# Quick Reference Guide

## Most Common Commands

### Build and Deploy
```bash
# Build the project
mvn clean package

# Deploy to Tomcat
cp target/forum.war $CATALINA_HOME/webapps/

# Start Tomcat
$CATALINA_HOME/bin/startup.sh

# Access application
http://localhost:8080/forum
```

### Testing
```bash
# Run all tests
mvn test

# Run only DBUnit tests
mvn test -Dtest=*DAOTest

# Run only Selenium tests
mvn test -Dtest=ForumSeleniumTest
```

### Database
```bash
# Create databases
mysql -u root -p -e "CREATE DATABASE forum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
mysql -u root -p -e "CREATE DATABASE forum_db_test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# Apply schema
mysql -u root -p forum_db < src/main/resources/schema.sql
mysql -u root -p forum_db_test < src/main/resources/schema.sql

# Load sample data
mysql -u root -p forum_db < src/main/resources/sample-data.sql
```

## Application URLs

- **Login**: `http://localhost:8080/forum/login`
- **Register**: `http://localhost:8080/forum/register`
- **Topics**: `http://localhost:8080/forum/topics`
- **New Topic**: `http://localhost:8080/forum/new-topic`
- **Ranking**: `http://localhost:8080/forum/ranking`

## Default Test Users (if using sample data)

| Username | Password    | Points |
|----------|-------------|--------|
| alice    | password123 | 50     |
| bob      | password123 | 35     |
| charlie  | password123 | 20     |
| diana    | password123 | 15     |
| eve      | password123 | 10     |

## Points System

- **Create Topic**: +10 points
- **Add Comment**: +3 points

## Project Structure

```
ITA-4-semana/
├── src/
│   ├── main/
│   │   ├── java/br/ita/forum/
│   │   │   ├── model/        (User, Topic, Comment)
│   │   │   ├── dao/          (Database access)
│   │   │   ├── service/      (Business logic)
│   │   │   └── servlet/      (Controllers)
│   │   ├── resources/        (db.properties, schema.sql)
│   │   └── webapp/
│   │       ├── WEB-INF/      (JSP files, web.xml)
│   │       └── css/          (style.css)
│   └── test/
│       ├── java/br/ita/forum/
│       │   ├── dao/          (DBUnit tests)
│       │   └── selenium/     (Selenium tests)
│       └── resources/        (test-dataset.xml)
├── pom.xml
└── Documentation files
```

## File Counts

- **Java Source Files**: 17
- **Java Test Files**: 4
- **JSP Pages**: 6
- **Documentation Files**: 6
- **Total Tests**: 22 (19 DBUnit + 3 Selenium)

## Configuration Files

### Database Configuration
- **Production**: `src/main/resources/db.properties`
- **Test**: `src/test/resources/db.properties`

### Web Configuration
- **Servlets**: `src/main/webapp/WEB-INF/web.xml`
- **Build**: `pom.xml`

## Troubleshooting Quick Fix

### Can't connect to database?
```bash
# Check MySQL is running
sudo systemctl status mysql

# Test connection
mysql -u root -p -e "SELECT 1;"
```

### Application won't start?
```bash
# Check Tomcat logs
tail -f $CATALINA_HOME/logs/catalina.out

# Check if port 8080 is available
lsof -i :8080  # Mac/Linux
netstat -ano | findstr :8080  # Windows
```

### Tests failing?
```bash
# Clean and rebuild
mvn clean compile

# Check test database
mysql -u root -p forum_db_test -e "SHOW TABLES;"

# Run tests with debug
mvn test -X
```

## Documentation Files

1. **README.md** - Start here
2. **DEPLOYMENT.md** - Deployment instructions
3. **TESTING.md** - Testing guide
4. **VIDEO_GUIDE.md** - Video recording guide
5. **PROJECT_SUMMARY.md** - Project overview
6. **SUBMISSION_CHECKLIST.md** - Pre-submission checklist
7. **QUICK_REFERENCE.md** - This file

## Key Classes

### Servlets (Controllers)
- `LoginServlet` - Handles login
- `RegisterServlet` - Handles registration
- `TopicsServlet` - Lists topics
- `NewTopicServlet` - Creates topics
- `TopicServlet` - Views topic and adds comments
- `RankingServlet` - Shows user ranking
- `LogoutServlet` - Handles logout

### DAOs (Data Access)
- `UserDAO` - User database operations
- `TopicDAO` - Topic database operations
- `CommentDAO` - Comment database operations

### Services (Business Logic)
- `UserService` - User business logic
- `TopicService` - Topic business logic
- `CommentService` - Comment business logic

## Technology Versions

- Java: 8
- Maven: 3.x
- MySQL: 8.0
- Servlet API: 3.1
- JSP: 2.3
- JUnit: 4.13.2
- DBUnit: 2.7.3
- Selenium: 4.11.0
- Tomcat: 8.5+ recommended

## Environment Variables

Set these if needed:

```bash
export JAVA_HOME=/path/to/jdk8
export CATALINA_HOME=/path/to/tomcat
export MAVEN_HOME=/path/to/maven
export PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin
```

## Common Issues

| Issue | Solution |
|-------|----------|
| Build fails | Run `mvn clean install` |
| Tests fail | Check database connection |
| 404 on /forum | Check WAR is deployed |
| Login fails | Check user exists in database |
| Points not updating | Check service layer logs |

## Next Steps

1. **First Time Setup**: Read DEPLOYMENT.md
2. **Running Tests**: Read TESTING.md
3. **Recording Video**: Read VIDEO_GUIDE.md
4. **Before Submitting**: Use SUBMISSION_CHECKLIST.md

## Support Resources

- MySQL Docs: https://dev.mysql.com/doc/
- Servlet API: https://javaee.github.io/servlet-spec/
- JSP Syntax: https://www.oracle.com/java/technologies/jspt.html
- DBUnit: http://dbunit.sourceforge.net/
- Selenium: https://www.selenium.dev/documentation/

## Emergency Contacts (Placeholder)

- Instructor Email: [Add here]
- TA Email: [Add here]
- Course Forum: [Add here]

---

**Quick Start in 3 Steps:**

1. Setup database and configure credentials
2. Build and deploy: `mvn clean package && cp target/forum.war $CATALINA_HOME/webapps/`
3. Access: `http://localhost:8080/forum`

**Good luck! 🚀**
