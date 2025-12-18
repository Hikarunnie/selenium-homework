package ge.tbc.testautomation;
import ge.tbc.testautomation.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class CommandsTest {
    WebDriver driver;
    WebDriverWait wait;  // had to add this, time between enable and disable was too long and assertions didn't work

    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://the-internet.herokuapp.com/dynamic_controls");
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description = "testing buttons", priority =1)
    public void buttonsTest(){
        WebElement enableDisableButton = driver.findElement(By.cssSelector("#input-example button"));
        enableDisableButton.click();

// Checking that input field is enabled and text 'It's enabled!' is displayed
        WebElement inputField = driver.findElement(By.xpath("//*[@id=\"input-example\"]/input"));
        wait.until(ExpectedConditions.elementToBeClickable(inputField));

        Assert.assertTrue(inputField.isEnabled());

        WebElement message = driver.findElement(By.id("message"));
        Assert.assertEquals(message.getText(), Constants.ENABLE_MESSAGE);

// checking button text has changed from Enable to Disable
        Assert.assertEquals(enableDisableButton.getText(), Constants.DISABLED_STATE);

        inputField.sendKeys("TBC IT Academy");
        inputField.clear();

    }

    @Test(description = "testing labels", priority = 2)
    public void labelsTest() {
        WebElement mainHeading = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/h4[1]"));
        Assert.assertEquals(mainHeading.getText(), Constants.MAIN_HEADING_TEXT);

        WebElement description = driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/p"));
        Assert.assertEquals(description.getText(), Constants.DESCRIPTION_TEXT);
    }

   @AfterClass
    public void tearDown(){
       driver.quit();
    }
}
