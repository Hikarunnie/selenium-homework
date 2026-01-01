package ge.tbc.testautomation;
import ge.tbc.testautomation.data.Constants;
import ge.tbc.testautomation.util.Util;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class FormsTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setUp(){
        driver=new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test(description ="custom dropdown test", priority =1)
    public void customDropDownTest(){
        driver.get("https://tympanus.net/Tutorials/CustomDropDownListStyling/");
        WebElement demo2 = driver.findElement(By.xpath("//a[text() ='Demo 2']"));
        demo2.click();

        WebElement dropDownMenu = driver.findElement(By.cssSelector("ul.dropdown"));
        Assert.assertFalse(dropDownMenu.isDisplayed());

        WebElement signInButton = driver.findElement(By.id("dd"));
        signInButton.click();

        boolean isDropDownVisible = wait.until(driverInstance -> dropDownMenu.isDisplayed());
        Assert.assertTrue(isDropDownVisible);

        Util.universalSelector(dropDownMenu, Constants.SELECTOR_TEXT); //worked ^^
    }

    @Test(description="nativeDropDownTest ", priority =2)
    public void  nativeDropDownTest (){
        driver.get("https://techcanvass.com/examples/register.html");
        WebElement radioButton = driver.findElement(By.cssSelector("input[type='radio'][value='female']"));
        if (!radioButton.isSelected()){
            radioButton.click();
        }
        Select selector = new Select(driver.findElement(By.cssSelector("select[name='model']")));
        Util.universalSelector(selector, Constants.NATIVE_SELECTOR_TEXT);

        //filling other fields
        WebElement firstName = driver.findElement(By.xpath("//input[@value ='First Name']"));
        firstName.sendKeys(Constants.FIRST_NAME);
        WebElement lastName = driver.findElement(By.xpath("//input[@value ='Last Name']"));
        lastName.sendKeys(Constants.LAST_NAME);
        WebElement address1 = driver.findElement(By.xpath("//input[@value ='Address1']"));
        address1.sendKeys(Constants.ADDRESS1);
        WebElement address2 = driver.findElement(By.xpath("//input[@value ='Address2']"));
        address2.sendKeys(Constants.ADDRESS2);
        WebElement city = driver.findElement(By.xpath("//input[@value ='City']"));
        city.sendKeys(Constants.CITY);
        WebElement contact1 = driver.findElement(By.xpath("//input[@value ='Contact1']"));
        contact1.sendKeys(Constants.CONTACT1);
        WebElement contact2 = driver.findElement(By.xpath("//input[@value ='Contact2']"));
        contact2.sendKeys(Constants.CONTACT2);
        WebElement checkBox = driver.findElement(By.xpath("//input[@name='DeliveryLoc']"));
        checkBox.click();

        //submitting
        WebElement registerButton = driver.findElement(By.xpath("//input[@value='Register']"));
        registerButton.click();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
