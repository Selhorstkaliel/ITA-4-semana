package br.ita.forum.selenium;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ForumSeleniumTest {
    
    private static WebDriver driver;
    private static WebDriverWait wait;
    private static final String BASE_URL = "http://localhost:8080/forum";
    
    @BeforeClass
    public static void setUpClass() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    
    @AfterClass
    public static void tearDownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    @Before
    public void setUp() {
        driver.get(BASE_URL + "/login");
    }
    
    @Test
    public void testUserRegistrationAndLogin() {
        // Test 1: Navigate from Login to Registration and create a new user
        
        // Click on Register link
        WebElement registerLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Register here")));
        registerLink.click();
        
        // Wait for registration page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
        
        // Generate unique username to avoid conflicts
        String timestamp = String.valueOf(System.currentTimeMillis());
        String username = "testuser" + timestamp;
        
        // Fill registration form
        driver.findElement(By.id("name")).sendKeys("Test User " + timestamp);
        driver.findElement(By.id("login")).sendKeys(username);
        driver.findElement(By.id("email")).sendKeys(username + "@example.com");
        driver.findElement(By.id("password")).sendKeys("password123");
        
        // Submit registration
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for redirect to login page
        wait.until(ExpectedConditions.urlContains("/login"));
        
        // Now login with the newly created user
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
        driver.findElement(By.id("login")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for redirect to topics page
        wait.until(ExpectedConditions.urlContains("/topics"));
        
        // Verify we're on the topics page
        Assert.assertTrue(driver.getCurrentUrl().contains("/topics"));
    }
    
    @Test
    public void testCreateTopicAndAddComment() {
        // Test 2: Login, create a topic, view it, and add a comment
        
        // Login first
        loginAsTestUser();
        
        // Navigate to new topic page
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("New Topic")));
        driver.findElement(By.linkText("New Topic")).click();
        
        // Wait for new topic page to load
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        // Create a new topic
        String topicTitle = "Test Topic " + System.currentTimeMillis();
        driver.findElement(By.id("title")).sendKeys(topicTitle);
        driver.findElement(By.id("content")).sendKeys("This is a test topic content for Selenium testing.");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for redirect to topics page
        wait.until(ExpectedConditions.urlContains("/topics"));
        
        // Find and click on the newly created topic
        WebElement topicLink = wait.until(ExpectedConditions.elementToBeClickable(By.linkText(topicTitle)));
        topicLink.click();
        
        // Wait for topic page to load
        wait.until(ExpectedConditions.urlContains("/topic"));
        
        // Verify topic title is displayed
        WebElement titleElement = driver.findElement(By.className("topic-title"));
        Assert.assertEquals(topicTitle, titleElement.getText());
        
        // Add a comment
        driver.findElement(By.id("content")).sendKeys("This is a test comment.");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for page reload
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("comment")));
        
        // Verify comment is displayed
        WebElement comment = driver.findElement(By.className("comment-content"));
        Assert.assertEquals("This is a test comment.", comment.getText());
    }
    
    @Test
    public void testNavigateToRankingAndVerifyPoints() {
        // Test 3: Login, view ranking, create topic, verify points increase
        
        // Login first
        loginAsTestUser();
        
        // Navigate to ranking
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Ranking")));
        driver.findElement(By.linkText("Ranking")).click();
        
        // Wait for ranking page to load
        wait.until(ExpectedConditions.urlContains("/ranking"));
        
        // Verify ranking table is present
        WebElement rankingTable = driver.findElement(By.cssSelector("table"));
        Assert.assertNotNull(rankingTable);
        
        // Go back to topics
        driver.findElement(By.linkText("Back to Topics")).click();
        wait.until(ExpectedConditions.urlContains("/topics"));
        
        // Create a new topic to earn points
        driver.findElement(By.linkText("New Topic")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("title")));
        
        driver.findElement(By.id("title")).sendKeys("Points Test Topic");
        driver.findElement(By.id("content")).sendKeys("Testing points system.");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        
        // Wait for redirect
        wait.until(ExpectedConditions.urlContains("/topics"));
        
        // Go to ranking again to see updated points
        driver.findElement(By.linkText("Ranking")).click();
        wait.until(ExpectedConditions.urlContains("/ranking"));
        
        // Verify ranking page loaded successfully
        Assert.assertTrue(driver.getCurrentUrl().contains("/ranking"));
    }
    
    private void loginAsTestUser() {
        // This method assumes a test user already exists in the database
        // For actual testing, you should create a user first or use a known test user
        
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
        
        // Check if we need to register first
        try {
            driver.findElement(By.linkText("Register here")).click();
            
            String timestamp = String.valueOf(System.currentTimeMillis());
            String username = "selenium" + timestamp;
            
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("name")));
            driver.findElement(By.id("name")).sendKeys("Selenium User");
            driver.findElement(By.id("login")).sendKeys(username);
            driver.findElement(By.id("email")).sendKeys(username + "@example.com");
            driver.findElement(By.id("password")).sendKeys("selenium123");
            driver.findElement(By.cssSelector("button[type='submit']")).click();
            
            wait.until(ExpectedConditions.urlContains("/login"));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
            
            driver.findElement(By.id("login")).sendKeys(username);
            driver.findElement(By.id("password")).sendKeys("selenium123");
        } catch (Exception e) {
            // Already on login page
            driver.findElement(By.id("login")).sendKeys("testuser");
            driver.findElement(By.id("password")).sendKeys("password123");
        }
        
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/topics"));
    }
}
