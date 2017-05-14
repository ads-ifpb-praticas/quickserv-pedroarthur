
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
public class ProfessionalRegisterST {
    
    private static WebDriver driver;
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
    }
    
    @Before
    public void setUp() {
        driver.get("http://localhost:8080/quickserv-web/faces/signup-professional.xhtml");
    }
    
    @AfterClass
    public static void tearDown() {
        driver.quit();
    }
    
    @Test
    public void testRegisterClient() {
        
        WebElement professionalEmail = driver.findElement(By.id("form-signup:username"));
        professionalEmail.sendKeys("professional@gmail.com");
        WebElement professionalPassword = driver.findElement(By.id("form-signup:password"));
        professionalPassword.sendKeys("123456");        
        WebElement professionalUserPhoto = driver.findElement(By.id("form-signup:userPhotoInput"));
        professionalUserPhoto.sendKeys("C:\\Users\\Taywens\\Pictures\\icons\\wow.png");
        WebElement professionalFirstname = driver.findElement(By.id("form-signup:firstname"));
        professionalFirstname.sendKeys("Professional 1");
        WebElement professionalLastname = driver.findElement(By.id("form-signup:lastname"));
        professionalLastname.sendKeys("de Pintura");
        WebElement professionalCpf = driver.findElement(By.id("form-signup:cpf"));
        professionalCpf.sendKeys("076.152.104-61");
        WebElement professionalPhone = driver.findElement(By.id("form-signup:phone"));
        professionalPhone.sendKeys("(88)88888-8888");
        WebElement professionalDocumentPhotoInput = driver.findElement(By.id("form-signup:documentPhotoInput"));
        professionalDocumentPhotoInput.sendKeys("C:\\Users\\Taywens\\Pictures\\icons\\wow.png");
        
        WebElement submitBtn = driver.findElement(By.id("form-signup:signin"));
        submitBtn.click();
        
        WebElement alertInfo = driver.findElement(By.className("alert-success"));
        String successMsg = alertInfo.getText();
        
        assertEquals("O Profissional Professional 1 foi salvo com sucesso!", successMsg);
    } 
    
}
