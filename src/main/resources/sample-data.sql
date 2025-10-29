-- Sample Data for Forum Application
-- Use this script to populate the database with test data

USE forum_db;

-- Insert sample users
INSERT INTO user (name, login, email, password, points) VALUES
('Alice Johnson', 'alice', 'alice@example.com', 'password123', 50),
('Bob Smith', 'bob', 'bob@example.com', 'password123', 35),
('Charlie Brown', 'charlie', 'charlie@example.com', 'password123', 20),
('Diana Prince', 'diana', 'diana@example.com', 'password123', 15),
('Eve Wilson', 'eve', 'eve@example.com', 'password123', 10);

-- Insert sample topics
INSERT INTO topic (title, content, user_id) VALUES
('Welcome to the Forum!', 'This is a welcome topic. Feel free to introduce yourself and ask questions!', 1),
('How to use this forum', 'Here are some guidelines on how to make the most of this forum. Be respectful and helpful!', 1),
('Java Programming Tips', 'Share your favorite Java programming tips and tricks here. What are your best practices?', 2),
('Web Development Discussion', 'Let''s discuss web development technologies, frameworks, and best practices.', 2),
('Database Design Best Practices', 'What are your thoughts on database design? Share your experiences and recommendations.', 3);

-- Insert sample comments
INSERT INTO comment (content, topic_id, user_id) VALUES
('Thanks for creating this forum! Excited to be here.', 1, 2),
('Great initiative! Looking forward to learning from everyone.', 1, 3),
('I''m new to forums. This guide is very helpful!', 2, 4),
('Always use meaningful variable names in your code!', 3, 1),
('Don''t forget to handle exceptions properly in Java.', 3, 3),
('Use PreparedStatements to prevent SQL injection!', 3, 4),
('I love using Spring Boot for web applications.', 4, 5),
('React and Node.js make a great combination!', 4, 1),
('Always normalize your database tables properly.', 5, 2),
('Use indexes wisely to improve query performance.', 5, 4),
('Remember the ACID properties for transactions!', 5, 1);

-- Verify the data
SELECT 'Users:' as Info;
SELECT id, name, login, points FROM user ORDER BY points DESC;

SELECT 'Topics:' as Info;
SELECT t.id, t.title, u.name as author FROM topic t JOIN user u ON t.user_id = u.id;

SELECT 'Comments:' as Info;
SELECT c.id, c.content, u.name as author, t.title as topic FROM comment c 
JOIN user u ON c.user_id = u.id 
JOIN topic t ON c.topic_id = t.id;

-- Display current ranking
SELECT 'Current Ranking:' as Info;
SELECT 
    ROW_NUMBER() OVER (ORDER BY points DESC, name ASC) as rank,
    name,
    login,
    points
FROM user
ORDER BY points DESC, name ASC;
