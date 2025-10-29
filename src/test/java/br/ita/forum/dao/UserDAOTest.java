package br.ita.forum.dao;

import br.ita.forum.model.User;
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

public class UserDAOTest extends DBTestCase {
    
    private UserDAO userDAO;
    
    public UserDAOTest() {
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
        userDAO = new UserDAO();
    }
    
    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    @Test
    public void testInsertUser() throws SQLException {
        User user = new User("New User", "newuser", "new@example.com", "newpass");
        userDAO.insert(user);
        
        assertTrue(user.getId() > 0);
        
        User retrieved = userDAO.findById(user.getId());
        assertNotNull(retrieved);
        assertEquals("New User", retrieved.getName());
        assertEquals("newuser", retrieved.getLogin());
        assertEquals("new@example.com", retrieved.getEmail());
    }
    
    @Test
    public void testFindByLogin() throws SQLException {
        User user = userDAO.findByLogin("testuser");
        
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals("testuser", user.getLogin());
        assertEquals("test@example.com", user.getEmail());
    }
    
    @Test
    public void testFindByLoginNotFound() throws SQLException {
        User user = userDAO.findByLogin("nonexistent");
        assertNull(user);
    }
    
    @Test
    public void testFindById() throws SQLException {
        User user = userDAO.findById(1);
        
        assertNotNull(user);
        assertEquals("Test User", user.getName());
        assertEquals("testuser", user.getLogin());
    }
    
    @Test
    public void testFindAllOrderedByPoints() throws SQLException {
        List<User> users = userDAO.findAllOrderedByPoints();
        
        assertNotNull(users);
        assertTrue(users.size() >= 2);
        
        // Check that first user has more or equal points than second
        assertTrue(users.get(0).getPoints() >= users.get(1).getPoints());
    }
    
    @Test
    public void testUpdateUser() throws SQLException {
        User user = userDAO.findById(1);
        user.setName("Updated Name");
        user.setEmail("updated@example.com");
        user.setPoints(50);
        
        userDAO.update(user);
        
        User updated = userDAO.findById(1);
        assertEquals("Updated Name", updated.getName());
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals(50, updated.getPoints());
    }
    
    @Test
    public void testAddPoints() throws SQLException {
        User user = userDAO.findById(1);
        int initialPoints = user.getPoints();
        
        userDAO.addPoints(1, 10);
        
        User updated = userDAO.findById(1);
        assertEquals(initialPoints + 10, updated.getPoints());
    }
    
    @Test
    public void testDeleteUser() throws SQLException {
        userDAO.delete(1);
        
        User user = userDAO.findById(1);
        assertNull(user);
    }
}
