package ge.tbc.testautomation;

import ge.tbc.testautomation.data.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.*;
import java.time.Duration;

public class JSexecutor {
    WebDriver driver;
    WebDriverWait wait;
    JavascriptExecutor js;

    @BeforeClass
    @Parameters("browserType")
    public void setUp(String browserType){
        if (browserType.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
           driver = new FirefoxDriver();
        }
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        js = (JavascriptExecutor) driver;
    }

@Test(description ="delete test", priority =1)
    public void deleteTest(){
        driver.get(Constants.WEBDRIVE_UNI_TO_DO_URL);
        WebElement practiceMagic = driver.findElement(By.xpath("//li[text() = ' Practice magic']"));
        js.executeScript("$(arguments[0]).hover();", practiceMagic);
        WebElement toTrash = practiceMagic.findElement(By.xpath(".//i"));
        js.executeScript("$(arguments[0]).click();", toTrash);

        wait.until(ExpectedConditions.invisibilityOf(practiceMagic));

        List<WebElement> items = driver.findElements(By.cssSelector("ul li"));
        Assert.assertFalse(items.contains(practiceMagic));
        Assert.assertEquals(items.size(), 2);
    }

   @Test(description ="scroll test", priority =2)
   public void scrollTest(){
        driver.get(Constants.TECHLISTIC_URL);
        //some pres weren't code examples
        List<WebElement> codeExmps =driver.findElements(By.xpath("//div[@class ='bg-black rounded-md mb-4']//parent::pre"));
        //behold horrible xpath, it had different structure had to select separately
        List<WebElement> secondTypeCodeExample = driver.findElements(By.xpath("//div[@style='background:#fff;border-width:.1em .1em .1em .8em;border:solid gray;overflow:auto;padding:.2em .6em;width:auto']//pre"));

        List<WebElement> allCodeExamples = new ArrayList<>();
        allCodeExamples.addAll(codeExmps);
        allCodeExamples.addAll(secondTypeCodeExample);

        Map<String, String> codeExamples = new HashMap<>();
        for (WebElement codeExmp: allCodeExamples){
            js.executeScript("arguments[0].scrollIntoView();",codeExmp);

            List<WebElement> headings = codeExmp.findElements(By.xpath("preceding::h3 | preceding::h4"));
            WebElement heading = headings.get(headings.size() - 1); //nearest
            String sectionName = heading.getText();

            String codeText = codeExmp.getText();
            codeExamples.put(sectionName,codeText);
        }

        Assert.assertEquals(codeExamples.size(),7);

       //
       WebElement popularTutorials = driver.findElement(By.xpath("//span[text()='Popular Tutorials']//parent::span//parent::div"));
       js.executeScript("arguments[0].scrollIntoView();",popularTutorials);
       List<WebElement> allLinks =popularTutorials
               .findElements(By.xpath(".//following-sibling::div//a"));

       Map<String,String> popularLinks = new HashMap<>();
       for (WebElement link: allLinks){
           String label = link.getText();
           String href = link.getAttribute("href");
           popularLinks.put(label,href);
       }
       Assert.assertEquals(popularLinks.size(),4);

       js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
   }

    @Test(description ="another scroll test", priority =3)
    public void anotherScrollTest(){
       driver.get(Constants.WEBDRIVE_UNI_SCROLLING_URL);
       WebElement leftBox = driver.findElement(By.id("zone2"));

       js.executeScript("arguments[0].scrollIntoView();",leftBox);

       String text = (String) js.executeScript("return arguments[0].textContent;", leftBox);
       Assert.assertTrue(text.contains(Constants.ENTRIES_TXT));
}

@AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
