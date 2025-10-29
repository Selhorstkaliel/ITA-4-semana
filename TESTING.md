# Testing Guide

This guide explains how to run and verify all tests in the Forum Web Application.

## Test Overview

The application includes three types of tests:

1. **DBUnit Tests** - Database layer testing (DAO classes)
2. **Selenium Tests** - UI/Browser automation tests
3. **Manual Tests** - User workflow verification

## Prerequisites

### For All Tests
- Java 8 or higher installed
- Maven 3.6 or higher installed
- MySQL Server running
- Database schemas created (forum_db and forum_db_test)

### For Selenium Tests
- ChromeDriver installed and in PATH
- Application deployed and running on Tomcat
- Chrome browser installed

## Database Setup for Tests

### Create Test Database

```sql
CREATE DATABASE forum_db_test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### Run Schema Script

```bash
mysql -u root -p forum_db_test < src/main/resources/schema.sql
```

### Configure Test Database

Edit `src/test/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/forum_db_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
db.username=root
db.password=root
db.driver=com.mysql.cj.jdbc.Driver
```

## Running DBUnit Tests

### Test Coverage

DBUnit tests cover the following DAO classes:

1. **UserDAO** (`UserDAOTest`)
   - Insert user
   - Find by login
   - Find by ID
   - Find all ordered by points
   - Update user
   - Add points
   - Delete user

2. **TopicDAO** (`TopicDAOTest`)
   - Insert topic
   - Find by ID
   - Find all topics
   - Update topic
   - Delete topic

3. **CommentDAO** (`CommentDAOTest`)
   - Insert comment
   - Find by ID
   - Find by topic ID
   - Update comment
   - Delete comment

### Run All DAO Tests

```bash
mvn test -Dtest=*DAOTest
```

### Run Individual Test Classes

```bash
# Test UserDAO only
mvn test -Dtest=UserDAOTest

# Test TopicDAO only
mvn test -Dtest=TopicDAOTest

# Test CommentDAO only
mvn test -Dtest=CommentDAOTest
```

### Run Specific Test Methods

```bash
mvn test -Dtest=UserDAOTest#testInsertUser
mvn test -Dtest=TopicDAOTest#testFindAll
mvn test -Dtest=CommentDAOTest#testFindByTopicId
```

### Expected Output

Successful test run should show:

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running br.ita.forum.dao.UserDAOTest
[INFO] Tests run: 8, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running br.ita.forum.dao.TopicDAOTest
[INFO] Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
[INFO] Running br.ita.forum.dao.CommentDAOTest
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
```

### Troubleshooting DBUnit Tests

**Problem**: "Cannot connect to database"

```bash
# Check MySQL is running
sudo systemctl status mysql

# Verify test database exists
mysql -u root -p -e "SHOW DATABASES LIKE 'forum_db_test';"

# Check credentials in src/test/resources/db.properties
```

**Problem**: "Table doesn't exist"

```bash
# Re-run schema script
mysql -u root -p forum_db_test < src/main/resources/schema.sql
```

## Running Selenium Tests

### Prerequisites

1. **Install ChromeDriver**

   **Windows:**
   ```bash
   # Download from https://chromedriver.chromium.org/
   # Add to PATH or place in project directory
   ```

   **macOS:**
   ```bash
   brew install chromedriver
   ```

   **Linux:**
   ```bash
   # Download and install
   wget https://chromedriver.storage.googleapis.com/LATEST_RELEASE
   VERSION=$(cat LATEST_RELEASE)
   wget https://chromedriver.storage.googleapis.com/$VERSION/chromedriver_linux64.zip
   unzip chromedriver_linux64.zip
   sudo mv chromedriver /usr/local/bin/
   sudo chmod +x /usr/local/bin/chromedriver
   ```

2. **Deploy Application**

   ```bash
   # Build WAR
   mvn clean package -DskipTests
   
   # Deploy to Tomcat
   cp target/forum.war $CATALINA_HOME/webapps/
   
   # Start Tomcat
   $CATALINA_HOME/bin/startup.sh
   
   # Verify application is running
   curl http://localhost:8080/forum/login
   ```

### Test Coverage

The Selenium test suite includes 3 tests:

1. **testUserRegistrationAndLogin**
   - Navigate from Login to Registration page
   - Fill registration form
   - Submit and verify redirect to login
   - Login with new credentials
   - Verify redirect to topics page

2. **testCreateTopicAndAddComment**
   - Login
   - Navigate to new topic page
   - Create a topic
   - View the topic
   - Add a comment
   - Verify comment appears

3. **testNavigateToRankingAndVerifyPoints**
   - Login
   - View ranking page
   - Create a topic
   - View ranking again
   - Verify points system

### Run Selenium Tests

```bash
# Ensure application is deployed and running first
mvn test -Dtest=ForumSeleniumTest
```

### Run Individual Selenium Tests

```bash
mvn test -Dtest=ForumSeleniumTest#testUserRegistrationAndLogin
mvn test -Dtest=ForumSeleniumTest#testCreateTopicAndAddComment
mvn test -Dtest=ForumSeleniumTest#testNavigateToRankingAndVerifyPoints
```

### Expected Output

```
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running br.ita.forum.selenium.ForumSeleniumTest
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0
```

### Troubleshooting Selenium Tests

**Problem**: "ChromeDriver not found"

```bash
# Check if ChromeDriver is in PATH
which chromedriver

# If not, download and add to PATH
export PATH=$PATH:/path/to/chromedriver
```

**Problem**: "Connection refused"

```bash
# Verify Tomcat is running
curl http://localhost:8080/forum/login

# Check Tomcat logs
tail -f $CATALINA_HOME/logs/catalina.out
```

**Problem**: "Element not found"

- Increase wait timeout in test
- Check if application URL is correct
- Verify application is fully loaded

## Running All Tests

### Run Complete Test Suite

```bash
# This will run both DBUnit and Selenium tests
mvn test
```

### Run Tests with Build

```bash
# Clean, compile, and test
mvn clean test
```

## Manual Testing Checklist

Even with automated tests, manual testing is important for user experience validation.

### 1. User Registration
- [ ] Navigate to registration page
- [ ] Fill form with valid data
- [ ] Submit and verify redirect to login
- [ ] Try registering with duplicate login (should fail)
- [ ] Try registering with empty fields (should fail)

### 2. User Login
- [ ] Login with valid credentials
- [ ] Verify redirect to topics page
- [ ] Verify welcome message shows username
- [ ] Try login with invalid credentials (should fail)
- [ ] Verify protected pages redirect to login when not authenticated

### 3. Topics Management
- [ ] View topics list
- [ ] Create new topic
- [ ] Verify topic appears in list
- [ ] Click on topic to view details
- [ ] Verify topic content displays correctly

### 4. Comments
- [ ] View topic with comments
- [ ] Add new comment
- [ ] Verify comment appears immediately
- [ ] Verify comment shows correct author

### 5. Points System
- [ ] Check initial points (0)
- [ ] Create topic and verify +10 points
- [ ] Add comment and verify +3 points
- [ ] Check ranking reflects correct points

### 6. Ranking
- [ ] View ranking page
- [ ] Verify users sorted by points
- [ ] Verify rank numbers are correct
- [ ] Verify all columns display correctly

### 7. Navigation
- [ ] Test all navigation links
- [ ] Verify back buttons work
- [ ] Test logout functionality
- [ ] Verify session is maintained across pages

### 8. Edge Cases
- [ ] Very long topic title
- [ ] Very long content
- [ ] Special characters in text
- [ ] Multiple rapid submissions
- [ ] Browser back button behavior

## Test Data Management

### Clean Test Database

```sql
USE forum_db_test;
DELETE FROM comment;
DELETE FROM topic;
DELETE FROM user;
ALTER TABLE user AUTO_INCREMENT = 1;
ALTER TABLE topic AUTO_INCREMENT = 1;
ALTER TABLE comment AUTO_INCREMENT = 1;
```

### Load Sample Data

```bash
mysql -u root -p forum_db < src/main/resources/sample-data.sql
```

### Backup Test Database

```bash
mysqldump -u root -p forum_db_test > forum_db_test_backup.sql
```

### Restore Test Database

```bash
mysql -u root -p forum_db_test < forum_db_test_backup.sql
```

## Continuous Integration

### Maven Profiles

Create different test profiles for different environments:

```xml
<profiles>
    <profile>
        <id>unit-tests</id>
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <includes>
                            <include>**/*DAOTest.java</include>
                        </includes>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
    
    <profile>
        <id>integration-tests</id>
        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-surefire-plugin</artifactId>
                    <configuration>
                        <includes>
                            <include>**/*SeleniumTest.java</include>
                        </includes>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

## Test Reports

### Generate Test Reports

```bash
mvn test
mvn surefire-report:report
```

View report at: `target/site/surefire-report.html`

### Test Coverage (Optional)

Install JaCoCo plugin for code coverage:

```bash
mvn clean test jacoco:report
```

View coverage at: `target/site/jacoco/index.html`

## Best Practices

1. **Isolation**: Each test should be independent
2. **Cleanup**: Tests should clean up after themselves
3. **Assertions**: Use meaningful assertion messages
4. **Data**: Don't rely on specific database state
5. **Speed**: Keep tests fast by minimizing I/O
6. **Reliability**: Tests should be deterministic

## Submission Checklist

Before submitting the project:

- [ ] All DBUnit tests pass (19 tests minimum)
- [ ] All 3 Selenium tests pass
- [ ] Manual testing completed
- [ ] Test databases are clean
- [ ] ChromeDriver version matches Chrome version
- [ ] Application builds without errors: `mvn clean package`
- [ ] WAR file deploys successfully
- [ ] Video demonstration recorded and uploaded

## Support

If you encounter issues:

1. Check this guide's troubleshooting sections
2. Review test logs in `target/surefire-reports/`
3. Check application logs in Tomcat
4. Verify database connectivity
5. Ensure all prerequisites are met

## Summary

Total Test Count:
- **DBUnit Tests**: 19 tests (UserDAO: 8, TopicDAO: 5, CommentDAO: 6)
- **Selenium Tests**: 3 tests
- **Total Automated Tests**: 22 tests

All tests should pass before considering the project complete.
