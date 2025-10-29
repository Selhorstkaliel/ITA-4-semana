# Video Demonstration Guide

This guide will help you create a comprehensive video demonstration of the Forum Web Application.

## Pre-Recording Checklist

- [ ] Application is deployed and running on Tomcat
- [ ] Database is set up and accessible
- [ ] Browser is ready (preferably Chrome or Firefox)
- [ ] Screen recording software is installed (OBS Studio, QuickTime, Windows Game Bar, etc.)
- [ ] Audio is working (for narration)
- [ ] Internet connection is stable (if uploading to YouTube)

## Recording Setup

### Screen Recording Tools

**Windows:**
- Windows Game Bar (Built-in): Press Win + G
- OBS Studio (Free): https://obsproject.com/

**macOS:**
- QuickTime Player (Built-in): File → New Screen Recording
- OBS Studio (Free): https://obsproject.com/

**Linux:**
- SimpleScreenRecorder: Available in most package managers
- OBS Studio: https://obsproject.com/

### Recording Settings

- **Resolution**: 1920x1080 (1080p) or 1280x720 (720p minimum)
- **Frame Rate**: 30 FPS minimum
- **Audio**: Enable microphone for narration (optional but recommended)
- **File Format**: MP4 (most compatible)

## Video Structure and Script

### Duration: 5-8 minutes

---

## Part 1: Introduction (30 seconds)

**What to show:**
- Forum application URL in browser: `http://localhost:8080/forum`
- Login page

**What to say:**
> "This is a complete forum web application built with Java Servlets, JSP, and JDBC following the MVC architecture. 
> The application allows users to register, create topics, add comments, and earn points based on their activity."

---

## Part 2: User Registration (1 minute)

**What to do:**
1. Click on "Register here" link
2. Fill in the registration form:
   - Name: "Demo User"
   - Login: "demouser"
   - Email: "demo@example.com"
   - Password: "demo123"
3. Click "Register" button
4. Show successful redirect to login page

**What to say:**
> "Let's start by registering a new user. I'll fill in the registration form with a name, unique login, email, and password.
> After submitting, the application validates the data and redirects us to the login page."

**Key points to highlight:**
- Form validation
- Unique login requirement
- Successful registration message

---

## Part 3: User Login (30 seconds)

**What to do:**
1. Enter login credentials:
   - Login: "demouser"
   - Password: "demo123"
2. Click "Login" button
3. Show successful redirect to Topics page

**What to say:**
> "Now I'll login with the credentials we just created. The application authenticates the user and creates a session."

**Key points to highlight:**
- Session management
- Welcome message with username
- Navigation links (New Topic, Ranking, Logout)

---

## Part 4: Topics Listing (30 seconds)

**What to do:**
1. Show the topics table (if using sample data)
2. Point out the table columns (Title, Author)
3. Hover over a topic link

**What to say:**
> "The topics page displays all forum topics with their titles and authors. Each topic is clickable to view its full content and comments."

**Key points to highlight:**
- Clean table layout
- Topic titles and authors
- Navigation options

---

## Part 5: Creating a Topic (1.5 minutes)

**What to do:**
1. Click "New Topic" link
2. Fill in the form:
   - Title: "Introduction to Java Servlets"
   - Content: "Java Servlets are powerful server-side components that handle HTTP requests and responses. They are the foundation of Java web applications."
3. Click "Create Topic" button
4. Show redirect back to topics page
5. **IMPORTANT**: Navigate to Ranking page and show points increased by 10

**What to say:**
> "Let's create a new topic. I'll click on 'New Topic' and fill in the title and content. When I submit this form, 
> the application will create the topic and award me 10 points. Let me check the ranking to confirm the points were added."

**Key points to highlight:**
- Topic creation form
- Text area for content
- Redirect to topics list after creation
- **Points system: +10 points for creating a topic**
- Updated ranking showing new points

---

## Part 6: Viewing a Topic (1 minute)

**What to do:**
1. Navigate back to Topics page
2. Click on the newly created topic
3. Show topic details:
   - Title
   - Author name
   - Full content
4. Scroll to show comments section (if any)
5. Show comment form

**What to say:**
> "Clicking on a topic takes us to the topic detail page where we can see the full content. 
> Below the topic, we can see all comments and a form to add new comments."

**Key points to highlight:**
- Topic display with full content
- Author information
- Comments section
- Comment form

---

## Part 7: Adding Comments (2 minutes)

**What to do:**
1. Add first comment:
   - Content: "Great topic! Servlets are indeed very important for Java web development."
   - Click "Add Comment"
2. Show page reload with new comment displayed
3. **IMPORTANT**: Navigate to Ranking and show points increased by 3
4. Return to topic page
5. Add second comment:
   - Content: "I would also recommend learning about JSP for the view layer."
   - Click "Add Comment"
6. Show the new comment
7. **IMPORTANT**: Navigate to Ranking again and show points increased by another 3

**What to say:**
> "Now I'll add a comment to this topic. After submitting, the page reloads and displays the new comment. 
> The application also awards me 3 points for this comment. Let me check the ranking... yes, my points increased by 3!
> I'll add another comment to further demonstrate the points system... and again, we can see the points increased by 3 more."

**Key points to highlight:**
- Comment submission
- Immediate display of new comments
- Author names on comments
- **Points system: +3 points per comment**
- **Cumulative points increase (3 + 3 = 6 total from comments)**

---

## Part 8: Ranking System (1.5 minutes)

**What to do:**
1. Click "Ranking" link
2. Show the ranking table:
   - Rank numbers
   - User names
   - Login names
   - Points
3. Point out the demo user's position
4. Explain the total points:
   - Initial: 0 points
   - +10 for topic creation
   - +3 for first comment
   - +3 for second comment
   - Total: 16 points

**What to say:**
> "The ranking page shows all users sorted by their points. As you can see, our demo user now has 16 points:
> 10 points from creating one topic and 6 points from two comments (3 points each). 
> The ranking is automatically updated as users participate in the forum."

**Key points to highlight:**
- Table with rank, name, login, and points
- Sorted by points (descending)
- Clear points calculation explanation
- Real-time updates

---

## Part 9: Navigation & Logout (30 seconds)

**What to do:**
1. Click "Back to Topics" link
2. Show topics page again
3. Click "Logout" link
4. Show redirect to login page
5. Try to access topics page directly (should redirect to login)

**What to say:**
> "The application has consistent navigation throughout. I can easily move between pages. 
> When I logout, my session is terminated and I'm redirected to the login page. 
> The application also protects authenticated pages - attempting to access them without login redirects to the login page."

**Key points to highlight:**
- Consistent navigation
- Logout functionality
- Session management
- Protected routes

---

## Part 10: Conclusion (30 seconds)

**What to show:**
- Login page or topics page

**What to say:**
> "This demonstrates a complete forum application built with Java Servlets, JSP, and JDBC following MVC architecture. 
> The application includes user registration and authentication, topic and comment management, and a points-based ranking system. 
> All requirements have been implemented including the database layer with JDBC, business logic in service classes, 
> and presentation with JSP views."

---

## Points Verification Summary

Make sure to clearly show in the video:

1. **Initial State**: User starts with 0 points after registration
2. **After Creating Topic**: User has 10 points (+10)
3. **After First Comment**: User has 13 points (+3)
4. **After Second Comment**: User has 16 points (+3)
5. **Ranking Table**: Shows user ranked by points with correct total

## Post-Recording

### Editing

1. **Trim** any unnecessary pauses or mistakes
2. **Add captions** if the audio is unclear (optional)
3. **Check duration**: Should be 5-8 minutes
4. **Verify quality**: Check video and audio clarity

### Export Settings

- **Format**: MP4 (H.264 codec)
- **Resolution**: 1920x1080 or 1280x720
- **Bitrate**: 8-10 Mbps for 1080p, 4-5 Mbps for 720p
- **Audio**: AAC, 128-192 kbps

### Uploading to YouTube

1. Go to: https://studio.youtube.com/
2. Click "Create" → "Upload videos"
3. Select your video file
4. Fill in details:
   - **Title**: "Forum Web Application - Java Servlets, JSP & JDBC (MVC)"
   - **Description**: Include project details and technologies used
   - **Visibility**: 
     - Public (visible to everyone)
     - Unlisted (only people with link can view)
     - Private (only you can view)
5. Click "Publish"
6. Copy the video link

### Alternative Platforms

If not using YouTube:
- **Vimeo**: https://vimeo.com/
- **Google Drive**: Upload and share link (set to "Anyone with the link")
- **Dropbox**: Upload and create share link
- **OneDrive**: Upload and share

## Troubleshooting

### Video Too Large

- Reduce resolution to 720p
- Lower bitrate
- Use HandBrake to compress: https://handbrake.fr/

### Poor Audio Quality

- Use external microphone
- Record in quiet environment
- Use audio editing software (Audacity) to enhance

### Recording Lag

- Close unnecessary applications
- Use lower resolution (720p)
- Record in shorter segments and combine

## Example Video Structure Timeline

```
00:00 - 00:30  Introduction and Login page
00:30 - 01:30  User Registration
01:30 - 02:00  User Login
02:00 - 02:30  Topics Listing
02:30 - 04:00  Create Topic + Check Ranking (10 points)
04:00 - 05:00  View Topic Details
05:00 - 07:00  Add Comments + Check Ranking (3+3 points)
07:00 - 08:30  Ranking Display and Explanation
08:30 - 09:00  Navigation and Logout
09:00 - 09:30  Conclusion
```

## Final Checklist

Before submitting:
- [ ] Video clearly shows all required features
- [ ] Points system is demonstrated (10 for topic, 3 for comment)
- [ ] Ranking is shown with correct point calculations
- [ ] Video quality is acceptable (720p minimum)
- [ ] Audio is clear (if included)
- [ ] Video is uploaded and link is accessible
- [ ] Video link works in incognito/private browsing mode
- [ ] Duration is appropriate (5-8 minutes)

## Sample Video Link Format

When submitting, provide the link in this format:

```
Video Demonstration: https://youtu.be/YOUR_VIDEO_ID
or
Video Demonstration: https://drive.google.com/file/d/YOUR_FILE_ID/view?usp=sharing
```

Good luck with your video demonstration!
