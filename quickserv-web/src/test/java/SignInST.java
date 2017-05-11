
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro Arthur
 */
public class SignInST {
    
    private static WebDriver driver;
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }
    
    @Before
    public void setUp() {
        driver.get("http://localhost:8080/quickserv-web/faces/index.xhtml");
    }
    
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
    
    @Test
    public void testRegisterClient() {
        
        WebElement professionalEmail = driver.findElement(By.id("signin-form:username"));
        professionalEmail.sendKeys("admin@admin.com");
        WebElement professionalPassword = driver.findElement(By.id("signin-form:password"));
        professionalPassword.sendKeys("admin");     
        
        WebElement submitBtn = driver.findElement(By.id("signin-form:signIn"));
        submitBtn.click();
        
        String currentUrl = driver.getCurrentUrl();
        
        assertEquals("http://localhost:8080/quickserv-web/faces/admin/home.xhtml", currentUrl);
    } 
    
}
