# Submission Checklist

Use this checklist to ensure all requirements are met before submitting the project.

## Pre-Submission Verification

### Build and Compilation
- [ ] Project builds successfully: `mvn clean package`
- [ ] No compilation errors
- [ ] WAR file created: `target/forum.war` (should be ~4-5 MB)
- [ ] All dependencies resolved

### Database
- [ ] Production database created: `forum_db`
- [ ] Test database created: `forum_db_test`
- [ ] Schema applied to both databases
- [ ] Database credentials configured in properties files
- [ ] Sample data loaded (optional but recommended)

### Tests
- [ ] All DBUnit tests pass (19 tests)
  - `mvn test -Dtest=UserDAOTest` - 8 tests pass
  - `mvn test -Dtest=TopicDAOTest` - 5 tests pass
  - `mvn test -Dtest=CommentDAOTest` - 6 tests pass
- [ ] All Selenium tests pass (3 tests)
  - `mvn test -Dtest=ForumSeleniumTest` - 3 tests pass
- [ ] Full test suite passes: `mvn test`

### Application Functionality
- [ ] Application deploys to Tomcat without errors
- [ ] Login page accessible at: `http://localhost:8080/forum/login`
- [ ] User registration works
- [ ] User login works
- [ ] Session management works
- [ ] Topics list displays correctly
- [ ] Can create new topic
- [ ] Topic creation awards 10 points
- [ ] Can view topic details
- [ ] Can add comments
- [ ] Comment creation awards 3 points
- [ ] Ranking page displays correctly
- [ ] Ranking shows correct point totals
- [ ] Logout works
- [ ] Protected pages redirect to login when not authenticated

### Code Quality
- [ ] Code follows MVC architecture
- [ ] Models in `br.ita.forum.model` package
- [ ] DAOs in `br.ita.forum.dao` package
- [ ] Services in `br.ita.forum.service` package
- [ ] Servlets in `br.ita.forum.servlet` package
- [ ] No business logic in JSPs
- [ ] No database access in Servlets (goes through Service/DAO)
- [ ] Proper exception handling
- [ ] PreparedStatements used (SQL injection prevention)
- [ ] Resources properly closed (try-with-resources)

### Documentation
- [ ] README.md complete and accurate
- [ ] DEPLOYMENT.md included
- [ ] TESTING.md included
- [ ] VIDEO_GUIDE.md included
- [ ] PROJECT_SUMMARY.md reviewed
- [ ] Code comments where necessary
- [ ] Database schema documented

## Submission Package Preparation

### 1. Project ZIP File

Create the project ZIP:
```bash
cd /path/to/parent/directory
zip -r forum-project.zip ITA-4-semana \
  -x "*/target/*" \
  -x "*/.git/*" \
  -x "*.class" \
  -x "*.jar" \
  -x "*.war" \
  -x "*/.idea/*" \
  -x "*/.settings/*" \
  -x "*.iml"
```

**ZIP File Checklist:**
- [ ] ZIP file created
- [ ] File size reasonable (< 5 MB without target folder)
- [ ] Contains `pom.xml`
- [ ] Contains `src/` directory with all code
- [ ] Contains all documentation files
- [ ] Contains SQL scripts
- [ ] Contains test files
- [ ] Does NOT contain `target/` folder
- [ ] Does NOT contain `.git/` folder
- [ ] Does NOT contain IDE-specific files

**Verify ZIP contents:**
```bash
unzip -l forum-project.zip | head -20
```

### 2. Selenium Test File

Extract the Selenium test:
```bash
cp src/test/java/br/ita/forum/selenium/ForumSeleniumTest.java .
```

**Selenium File Checklist:**
- [ ] File extracted: `ForumSeleniumTest.java`
- [ ] File compiles independently
- [ ] File contains all 3 test methods
- [ ] File has proper package declaration
- [ ] File includes all necessary imports

### 3. Video Demonstration

Record video following VIDEO_GUIDE.md:

**Video Checklist:**
- [ ] Video recorded (5-8 minutes)
- [ ] Shows user registration
- [ ] Shows user login
- [ ] Shows topics listing
- [ ] Shows topic creation
- [ ] Shows points increase (+10) after topic creation
- [ ] Shows topic viewing
- [ ] Shows comment creation
- [ ] Shows points increase (+3) after comment
- [ ] Shows ranking page
- [ ] Shows correct point calculations
- [ ] Video quality is acceptable (720p minimum)
- [ ] Audio is clear (if included)
- [ ] Video uploaded to platform (YouTube, Google Drive, etc.)
- [ ] Video link is accessible
- [ ] Video link tested in private/incognito mode

**Video Link Format:**
```
Video Link: https://youtu.be/YOUR_VIDEO_ID
or
Video Link: https://drive.google.com/file/d/YOUR_FILE_ID/view?usp=sharing
```

## Final Submission Files

You should have THREE items ready to submit:

### ✅ 1. Project ZIP File
- **Filename**: `forum-project.zip` or `ITA-4-semana.zip`
- **Size**: < 5 MB (without target folder)
- **Contents**: Complete project source code, tests, and documentation

### ✅ 2. Selenium Test File
- **Filename**: `ForumSeleniumTest.java`
- **Size**: ~8-10 KB
- **Contents**: Complete Selenium test class with all 3 tests

### ✅ 3. Video Demonstration Link
- **Format**: URL to publicly accessible video
- **Duration**: 5-8 minutes
- **Platform**: YouTube (unlisted or public), Google Drive, Vimeo, etc.

## Submission Platform Checklist

Depending on your submission platform:

### If submitting via Web Form
- [ ] All three files uploaded
- [ ] File names are correct
- [ ] Video link is in correct field
- [ ] Submission confirmation received

### If submitting via Email
- [ ] Email subject line includes project name
- [ ] All three items attached or linked
- [ ] Video link is clickable
- [ ] Email sent to correct address
- [ ] Confirmation email received

### If submitting via LMS (Moodle, Canvas, etc.)
- [ ] Correct assignment selected
- [ ] All files uploaded to correct fields
- [ ] Video link added to description or separate field
- [ ] Submission completed
- [ ] Submission receipt/confirmation

## Post-Submission Verification

After submitting:

- [ ] Download your submitted files to verify they're correct
- [ ] Click video link to ensure it works
- [ ] Extract and verify ZIP file contents
- [ ] Keep backup of all files
- [ ] Save confirmation email/receipt

## Common Issues to Check

### Build Issues
- ❌ Maven build fails → Check `pom.xml` for errors
- ❌ Dependencies not resolved → Run `mvn clean install`
- ❌ Java version mismatch → Ensure Java 8 is used

### Test Issues
- ❌ DBUnit tests fail → Check database connection and schema
- ❌ Selenium tests fail → Ensure ChromeDriver is installed and app is running
- ❌ Connection refused → Check Tomcat is running

### Deployment Issues
- ❌ 404 error → Check WAR is deployed and context path is correct
- ❌ Database error → Verify db.properties has correct credentials
- ❌ ClassNotFoundException → Ensure MySQL driver is in WAR

### Video Issues
- ❌ Video too large → Reduce resolution or use compression
- ❌ Video link not accessible → Check sharing settings
- ❌ Video quality poor → Re-record with higher settings

## Grading Criteria Reference

Expected grading points:

1. **Functionality** (40%)
   - All screens working
   - User registration/login
   - Topic creation
   - Comment system
   - Points system
   - Ranking

2. **Architecture** (30%)
   - MVC pattern correctly implemented
   - Proper layer separation
   - Service layer between Servlet and DAO
   - No business logic in views

3. **Testing** (20%)
   - DBUnit tests for all DAOs
   - At least 3 Selenium tests
   - Tests pass successfully

4. **Code Quality** (10%)
   - Clean, readable code
   - Proper exception handling
   - Resource management
   - SQL injection prevention

## Final Checklist Summary

Before submitting, ensure ALL of these are checked:

### Critical Items
- [ ] ✅ Application builds and runs
- [ ] ✅ All 22 tests pass (19 DBUnit + 3 Selenium)
- [ ] ✅ Video demonstrates all features
- [ ] ✅ ZIP file contains complete project
- [ ] ✅ Selenium test file extracted
- [ ] ✅ Video link is accessible

### Required Features
- [ ] ✅ User registration and login
- [ ] ✅ Topics listing and creation
- [ ] ✅ Comment creation
- [ ] ✅ Points system (10 for topic, 3 for comment)
- [ ] ✅ Ranking by points
- [ ] ✅ MVC architecture

### Technical Requirements
- [ ] ✅ Uses Servlets (not other frameworks)
- [ ] ✅ Uses JSP for views
- [ ] ✅ Uses JDBC for database (not ORM)
- [ ] ✅ Database schema as specified
- [ ] ✅ DBUnit tests for DAO layer
- [ ] ✅ Selenium tests for UI

### Documentation
- [ ] ✅ README with setup instructions
- [ ] ✅ Deployment guide
- [ ] ✅ Testing guide
- [ ] ✅ Code is documented

## Confidence Check

Answer these questions:

1. Can you build the project without errors? **YES / NO**
2. Do all tests pass? **YES / NO**
3. Can you deploy and access the application? **YES / NO**
4. Does the video show all required features? **YES / NO**
5. Is the ZIP file complete? **YES / NO**

If all answers are YES, you're ready to submit! 🎉

If any answer is NO, review the relevant section in this checklist and fix the issue before submitting.

---

**Good luck with your submission!**

Remember: Quality over speed. Take time to verify everything works correctly.
