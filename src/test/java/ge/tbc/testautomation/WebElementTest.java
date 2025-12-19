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

public class WebElementTest {
    WebDriver driver;

    @BeforeClass
      public void setUp(){
         driver = new ChromeDriver();
         driver.manage().window().maximize();
         driver.get("http://the-internet.herokuapp.com/drag_and_drop");
     }

    @Test(description = "testing dimensions", priority = 1)
    public void dimensionsTest(){
        WebElement columnA = driver.findElement(By.id("column-a"));
        WebElement columnB = driver.findElement(By.id("column-b"));

        int aColumnY = columnA.getLocation().getY();
        int bColumnY = columnB.getLocation().getY();
        Assert.assertEquals(aColumnY, bColumnY);

        Assert.assertEquals(columnA.getAttribute("draggable"), "true");
        Assert.assertEquals(columnB.getAttribute("draggable"), "true");

    }

    @Test(description = "testing list", priority = 2)
    public void linkTest(){
        WebElement elementalSelenium = driver.findElement(By.linkText("Elemental Selenium"));
        Assert.assertEquals(elementalSelenium.getAttribute("href"), Constants.ELEMENTAL_SELENIUM_LINK);
    }

    @AfterClass
    public void tearDown(){
         driver.quit();
    }
}
