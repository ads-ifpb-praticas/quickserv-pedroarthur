
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
public class ClientRegisterST {
    
    private static WebDriver driver;
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }
    
    @Before
    public void setUp() {
        driver.get("http://localhost:8080/quickserv-web/faces/signup-client.xhtml");
    }
    
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
    
    @Test
    public void testRegisterClient() {
        
        WebElement clientEmail = driver.findElement(By.id("form-signup:username"));
        clientEmail.sendKeys("pfernandesvasconcelos@gmail.com");
        WebElement clientPassword = driver.findElement(By.id("form-signup:password"));
        clientPassword.sendKeys("123456");        
        WebElement clientPhoto = driver.findElement(By.id("form-signup:userPhotoInput"));
        clientPhoto.sendKeys("C:\\Users\\Taywens\\Pictures\\icons\\wow.png");
        WebElement clientFirstname = driver.findElement(By.id("form-signup:firstname"));
        clientFirstname.sendKeys("Pedro Arthur");
        WebElement clientLastname = driver.findElement(By.id("form-signup:lastname"));
        clientLastname.sendKeys("Fernandes de Vasconcelos");
        WebElement clientCpf = driver.findElement(By.id("form-signup:cpf"));
        clientCpf.sendKeys("211.406.257-05");
        WebElement clientPhone = driver.findElement(By.id("form-signup:phone"));
        clientPhone.sendKeys("(99)99999-9999");
        
        WebElement submitBtn = driver.findElement(By.id("form-signup:signin"));
        submitBtn.click();
        
        WebElement alertInfo = driver.findElement(By.className("alert-success"));
        String successMsg = alertInfo.getText();
        
        assertEquals("O Cliente Pedro Arthur foi salvo com sucesso!", successMsg);
    } 
    
}
