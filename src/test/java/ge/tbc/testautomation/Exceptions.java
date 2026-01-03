package ge.tbc.testautomation;
import ge.tbc.testautomation.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.Duration;


public class Exceptions {
    WebDriver driver;

    @BeforeClass
    @Parameters("browserType")
    public void setUp(String browserType) {
        if (browserType.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        driver.get(Constants.ANIME_PLANET_URL);
    }
    @Test(description = "testing exceptions" ,priority = 1)
    public void differentExceptionTest(){
        // ElementNotInteractableException - I try to click radio type input even tho it's present,it has class="hidden"
        try {
            WebElement element= driver.findElement(By.id("mNavToggle"));
        } catch (Exception e) {
            System.out.println("element is present but not visible");
        }

        //NoSuchElementException - I try to find  element that does not exist
        try {
            WebElement element = driver.findElement(By.cssSelector("button#notexistent"));
            element.click();
        } catch (Exception e) {
            System.out.println("element not found in DOM");
        }

        //StaleElementReferenceException - element 1 had class selected but after i selected other element
        // and tried to reaccess it after it was no longer selected it became stale element reference was shown
        try {
            WebElement element = driver.findElement(By.xpath("//li[@class='selected']/a[text()='Popular']"));
            WebElement element1 = driver.findElement(By.xpath("//a[text()='Name']"));
            element1.click();
            element.click();
        } catch (Exception e) {
            System.out.println("stale element reference");
        }

        //NoSuchFrameException - no such frame exists to switch to
        try {
            driver.switchTo().frame("nonExistingFrame");
        } catch (Exception e) {
            System.out.println("Frame was not found");
        }

        // NoSuchWindowException -trying to switch to a non-existing window
        try {
            driver.switchTo().window("nonExistingWindow");
        } catch (Exception e) {
            System.out.println("Window not found");
        }

        // NoAlertPresentException - im trying to switch to an alert when no alert is present
        try {
            driver.switchTo().alert();
        } catch (Exception e) {
            System.out.println(" No alert present on the page");
        }

        // TimeoutException -waiting for an element that never becomes visible within timeout ^ ^
        try {
             new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.visibilityOfElementLocated(By.id("mSearchToggle")));
        } catch (Exception e) {
            System.out.println("Command did not complete in time");
        }

        // NoSuchSessionException- coz i tried to use a WebDriver instance after calling quit()
        // and closing the browser/destroying current  session
        try {
            driver.quit();
            driver.get(Constants.ANIME_PLANET_URL);
        } catch (Exception e) {
            System.out.println("Cannot interact with a terminated session");
        }
    }

@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
