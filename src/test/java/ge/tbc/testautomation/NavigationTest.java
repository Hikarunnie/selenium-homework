package ge.tbc.testautomation;
import ge.tbc.testautomation.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NavigationTest {
    WebDriver driver;

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://ultimateqa.com/automation");
    }

    @Test(description = "testing going to services and back")
    public void goToServicesAndBack(){
        WebElement servicesClick = driver.findElement(By.xpath("//*[@id=\"menu-item-218392\"]/a"));
        servicesClick.click();

        String serviceUrl = driver.getCurrentUrl();
        Assert.assertEquals(serviceUrl, Constants.SERVICES_URL);

        driver.navigate().back();

        String automationUrl = driver.getCurrentUrl();
        Assert.assertEquals(automationUrl, Constants.AUTOMATION_URL);

    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }


}
