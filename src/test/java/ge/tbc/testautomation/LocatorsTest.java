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
import java.util.List;

public class LocatorsTest {
    WebDriver driver;
    @BeforeClass
    public void setUp(){
        driver = new ChromeDriver();
        driver.manage().window().maximize();

    }

    @Test(description ="unordered list test" , priority = 1)
    public void unorderedListTest(){
        driver.get("https://jqueryui.com/slider/");
        WebElement aside = driver.findElement(By.xpath("//h3[text()='Effects' and @class = 'widget-title']/parent::aside"));
        List<WebElement> liElements = aside.findElements(By.xpath("./ul/li"));
        List<WebElement> oLiElements = liElements.stream()
                .filter(li -> li.getText().contains(Constants.FILTER_LETTER))
                .toList();

        oLiElements.stream().parallel().forEach(
                li -> {
                    WebElement a = li.findElement(By.xpath("./a"));
                    String aHref = a.getAttribute("href");
                    if (!aHref.contains(Constants.HREF_CONTAINS)) {
                        System.out.println(aHref);
                    }
                }
        );
    }

    @Test(description ="buttons Test" , priority = 2)
    public void buttonsTest() {
        driver.get("http://the-internet.herokuapp.com/add_remove_elements/");
        WebElement addElementButton = driver.findElement(By.xpath("//button[text() ='Add Element']"));
        for (int i = 0; i < 3; i++) {
        addElementButton.click();
        }

        WebElement lastDeleteBtn = driver.findElement(By.xpath("//div[@id ='elements']//button[last()]"));
        Assert.assertEquals(lastDeleteBtn.getAttribute("class"),Constants.BUTTON_CLASS);

        List<WebElement> buttons = driver.findElements(By.cssSelector("button[class^='added']"));
        WebElement lastButton =  buttons.get(buttons.size()-1);
        Assert.assertEquals(lastButton.getAttribute("onClick"),Constants.ONCLICK_STATUS);
    }

    @Test(description ="challenging Dom Test" , priority = 3)
     public void challengingDomTest() {
        driver.get("http://the-internet.herokuapp.com/challenging_dom");

        List<WebElement> trHeaders = driver.findElements(By.xpath("//thead/tr/th"));
        int loremIndex = 0;
        int ipsumIndex = 0;
        for(int i=0;i <trHeaders.size();i++){
             WebElement th = trHeaders.get(i);
             if(th.getText().equals(Constants.LOREM))   {
                 loremIndex= i+1;
             }
             if(th.getText().equals(Constants.IPSUM)){
                 ipsumIndex= i+1;
             }
        }
        String targetAdress = String.format("//tbody/tr[td[%d]='%s']/td[%d]",ipsumIndex,Constants.IPSUM_VALUE,loremIndex);
        WebElement target = driver.findElement(By.xpath(targetAdress));
        System.out.println(target.getText());
      }
      
    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
