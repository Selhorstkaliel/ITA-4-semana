package br.ita.forum.dao;

import br.ita.forum.model.Comment;
import br.ita.forum.model.Topic;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class CommentDAOTest extends DBTestCase {
    
    private CommentDAO commentDAO;
    private TopicDAO topicDAO;
    
    public CommentDAOTest() {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, 
            "jdbc:mysql://localhost:3306/forum_db_test?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "root");
    }
    
    @Override
    protected IDataSet getDataSet() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("test-dataset.xml");
        return new FlatXmlDataSetBuilder().build(is);
    }
    
    @Override
    protected DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.CLEAN_INSERT;
    }
    
    @Override
    protected DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }
    
    @Before
    public void setUp() throws Exception {
        super.setUp();
        commentDAO = new CommentDAO();
        topicDAO = new TopicDAO();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testInsertComment() throws SQLException {
        // First create a topic
        Topic topic = new Topic("Test Topic", "Test content", 1);
        topicDAO.insert(topic);
        
        Comment comment = new Comment("Test comment", topic.getId(), 1);
        commentDAO.insert(comment);
        
        assertTrue(comment.getId() > 0);
        
        Comment retrieved = commentDAO.findById(comment.getId());
        assertNotNull(retrieved);
        assertEquals("Test comment", retrieved.getContent());
    }
    
    @Test
    public void testFindById() throws SQLException {
        Topic topic = new Topic("Test Topic", "Test content", 1);
        topicDAO.insert(topic);
        
        Comment comment = new Comment("Sample comment", topic.getId(), 2);
        commentDAO.insert(comment);
        
        Comment found = commentDAO.findById(comment.getId());
        
        assertNotNull(found);
        assertEquals("Sample comment", found.getContent());
        assertNotNull(found.getUserName());
    }
    
    @Test
    public void testFindByTopicId() throws SQLException {
        Topic topic = new Topic("Test Topic", "Test content", 1);
        topicDAO.insert(topic);
        
        Comment comment1 = new Comment("Comment 1", topic.getId(), 1);
        Comment comment2 = new Comment("Comment 2", topic.getId(), 2);
        
        commentDAO.insert(comment1);
        commentDAO.insert(comment2);
        
        List<Comment> comments = commentDAO.findByTopicId(topic.getId());
        
        assertNotNull(comments);
        assertEquals(2, comments.size());
    }
    
    @Test
    public void testUpdateComment() throws SQLException {
        Topic topic = new Topic("Test Topic", "Test content", 1);
        topicDAO.insert(topic);
        
        Comment comment = new Comment("Original comment", topic.getId(), 1);
        commentDAO.insert(comment);
        
        comment.setContent("Updated comment");
        commentDAO.update(comment);
        
        Comment updated = commentDAO.findById(comment.getId());
        assertEquals("Updated comment", updated.getContent());
    }
    
    @Test
    public void testDeleteComment() throws SQLException {
        Topic topic = new Topic("Test Topic", "Test content", 1);
        topicDAO.insert(topic);
        
        Comment comment = new Comment("Comment to delete", topic.getId(), 1);
        commentDAO.insert(comment);
        
        int id = comment.getId();
        commentDAO.delete(id);
        
        Comment deleted = commentDAO.findById(id);
        assertNull(deleted);
    }
}
