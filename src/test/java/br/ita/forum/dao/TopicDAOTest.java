package br.ita.forum.dao;

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

public class TopicDAOTest extends DBTestCase {
    
    private TopicDAO topicDAO;
    
    public TopicDAOTest() {
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
        topicDAO = new TopicDAO();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testInsertTopic() throws SQLException {
        Topic topic = new Topic("Test Topic", "This is test content", 1);
        topicDAO.insert(topic);
        
        assertTrue(topic.getId() > 0);
        
        Topic retrieved = topicDAO.findById(topic.getId());
        assertNotNull(retrieved);
        assertEquals("Test Topic", retrieved.getTitle());
        assertEquals("This is test content", retrieved.getContent());
        assertEquals(1, retrieved.getUserId());
    }
    
    @Test
    public void testFindById() throws SQLException {
        Topic topic = new Topic("Sample Topic", "Sample content", 1);
        topicDAO.insert(topic);
        
        Topic found = topicDAO.findById(topic.getId());
        
        assertNotNull(found);
        assertEquals("Sample Topic", found.getTitle());
        assertEquals("Sample content", found.getContent());
        assertNotNull(found.getUserName());
    }
    
    @Test
    public void testFindAll() throws SQLException {
        Topic topic1 = new Topic("Topic 1", "Content 1", 1);
        Topic topic2 = new Topic("Topic 2", "Content 2", 2);
        
        topicDAO.insert(topic1);
        topicDAO.insert(topic2);
        
        List<Topic> topics = topicDAO.findAll();
        
        assertNotNull(topics);
        assertTrue(topics.size() >= 2);
    }
    
    @Test
    public void testUpdateTopic() throws SQLException {
        Topic topic = new Topic("Original Title", "Original content", 1);
        topicDAO.insert(topic);
        
        topic.setTitle("Updated Title");
        topic.setContent("Updated content");
        topicDAO.update(topic);
        
        Topic updated = topicDAO.findById(topic.getId());
        assertEquals("Updated Title", updated.getTitle());
        assertEquals("Updated content", updated.getContent());
    }
    
    @Test
    public void testDeleteTopic() throws SQLException {
        Topic topic = new Topic("Topic to Delete", "Content", 1);
        topicDAO.insert(topic);
        
        int id = topic.getId();
        topicDAO.delete(id);
        
        Topic deleted = topicDAO.findById(id);
        assertNull(deleted);
    }
}
