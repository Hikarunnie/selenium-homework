package ge.tbc.testautomation;
import ge.tbc.testautomation.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class WaitsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description = "wait For Disappearance", priority = 1)
   public void waitForDisappearance(){
        driver.get("http://the-internet.herokuapp.com/dynamic_controls");
        WebElement enableDisableButton = driver.findElement(By.cssSelector("#input-example > button"));
        enableDisableButton.click();

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("#loading")
        ));

        Assert.assertEquals(enableDisableButton.getText(),Constants.DISABLED_STATE);

        WebElement inputAboveDisabled = driver.findElement(By.xpath("//button[text()='Disable']//preceding-sibling::input")); //this crazy xpath just coz it said input above disable T_T
        inputAboveDisabled.sendKeys(Constants.ACCESS_TEXT);

   }

    @Test(description = "wait For Disappearance", priority = 2)
   void waitForText(){
        driver.get("https://demoqa.com/progress-bar");
        WebElement startStopButton = driver.findElement(By.xpath("//button[@id='startStopButton']")); //By.id didn't work
        startStopButton.click();

        wait.until(ExpectedConditions.textToBe(By.cssSelector("div.progress-bar"),"100%"));

        WebElement resetButton = driver.findElement(By.id("resetButton")); //refinding coz button changed
        Assert.assertEquals(resetButton.getText(), Constants.START_BUTTON_STATUS);
   }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
