# Forum Web Application

A complete forum web application developed with Servlets, JSP, and JDBC following the MVC architecture pattern.

## Features

- User registration and authentication
- Topic creation and viewing
- Comment system
- Points/ranking system
- User rankings based on activity

## Points System

- Create a topic: **10 points**
- Add a comment: **3 points**

## Technology Stack

- **Java 8**
- **Servlets 3.1** - Controllers
- **JSP** - Views
- **JDBC** - Database access
- **MySQL** - Database
- **Maven** - Build tool
- **DBUnit** - Database testing
- **Selenium** - UI testing
- **JUnit 4** - Unit testing

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── br/ita/forum/
│   │       ├── model/          # Data models (User, Topic, Comment)
│   │       ├── dao/            # Data Access Objects
│   │       ├── service/        # Business logic layer
│   │       └── servlet/        # Controllers
│   ├── resources/
│   │   ├── db.properties       # Database configuration
│   │   └── schema.sql          # Database schema
│   └── webapp/
│       ├── WEB-INF/
│       │   ├── *.jsp           # View templates
│       │   └── web.xml         # Web application config
│       └── css/
│           └── style.css       # Styles
└── test/
    ├── java/
    │   └── br/ita/forum/
    │       ├── dao/            # DAO tests with DBUnit
    │       └── selenium/       # Selenium UI tests
    └── resources/
        ├── test-dataset.xml    # Test data
        └── db.properties       # Test database config
```

## Database Schema

The application uses the following database structure:

### User Table
- `id` (INT, Primary Key, Auto Increment)
- `name` (VARCHAR)
- `login` (VARCHAR, Unique)
- `email` (VARCHAR)
- `password` (VARCHAR)
- `points` (INT)
- `created_at` (TIMESTAMP)

### Topic Table
- `id` (INT, Primary Key, Auto Increment)
- `title` (VARCHAR)
- `content` (TEXT)
- `user_id` (INT, Foreign Key)
- `created_at` (TIMESTAMP)

### Comment Table
- `id` (INT, Primary Key, Auto Increment)
- `content` (TEXT)
- `topic_id` (INT, Foreign Key)
- `user_id` (INT, Foreign Key)
- `created_at` (TIMESTAMP)

## Setup Instructions

### 1. Database Setup

1. Install MySQL Server
2. Create the database:
```sql
CREATE DATABASE forum_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

3. Create the test database:
```sql
CREATE DATABASE forum_db_test CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

4. Run the schema script:
```bash
mysql -u root -p forum_db < src/main/resources/schema.sql
mysql -u root -p forum_db_test < src/main/resources/schema.sql
```

5. Update database credentials in:
   - `src/main/resources/db.properties`
   - `src/test/resources/db.properties`

### 2. Build the Project

```bash
mvn clean install
```

### 3. Deploy to Application Server

1. Deploy the generated WAR file (`target/forum.war`) to your application server (Tomcat, GlassFish, etc.)
2. Access the application at: `http://localhost:8080/forum`

## Running Tests

### Unit Tests (DBUnit)

Run DAO tests with DBUnit:
```bash
mvn test -Dtest=*DAOTest
```

### Selenium Tests

1. Ensure ChromeDriver is installed and in your PATH
2. Start the application server
3. Run Selenium tests:
```bash
mvn test -Dtest=ForumSeleniumTest
```

## Application Screens

### 1. Login Screen
- Initial screen with login and password fields
- Link to registration page
- Error messages for invalid credentials

### 2. Registration Screen
- Form with name, login, email, and password fields
- Validation for duplicate logins
- Redirects to login after successful registration

### 3. Topics Screen
- Lists all topics with title and author
- Links to create new topic, ranking, and logout
- Click on topic to view details

### 4. New Topic Screen
- Form to create a new topic with title and content
- Awards 10 points to the user upon creation
- Redirects to topics list after creation

### 5. Topic Detail Screen
- Displays topic title, author, and content
- Shows all comments with authors
- Form to add new comments
- Awards 3 points for each comment

### 6. Ranking Screen
- Table showing user rankings
- Columns: Rank, Name, Login, Points
- Sorted by points (descending)

## Testing Checklist

The application includes:
- ✅ DBUnit tests for UserDAO
- ✅ DBUnit tests for TopicDAO
- ✅ DBUnit tests for CommentDAO
- ✅ 3 Selenium tests covering:
  1. User registration and login flow
  2. Topic creation and comment addition
  3. Ranking navigation and points verification

## Video Demonstration

A video demonstration should show:
1. User registration
2. User login
3. Topics listing
4. Topic creation (+10 points)
5. Topic viewing
6. Comment creation (+3 points)
7. Ranking display with updated points

## Notes

- The application follows MVC architecture strictly
- No additional frameworks beyond Servlet, JSP, and JDBC are used
- Points are automatically awarded for user actions
- All database operations use JDBC
- Session management handles user authentication
- Input validation is performed on both client and server side

## License

MIT License