package ge.tbc.testautomation.util;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class Util {
   public static <T> void universalSelector(T element,  String visibleText){
        if(element instanceof Select){
            ((Select) element).selectByVisibleText(visibleText);
        }
        else if (element instanceof WebElement) {
            WebElement element1 = (WebElement) element;
            List<WebElement> options = element1.findElements(By.xpath(".//following-sibling::div | .//li/a"));

            WebElement target = options.stream().filter(el ->
                    el.getText().contains(visibleText))
                    .findFirst()
                    .orElseThrow(()-> new NoSuchElementException("no such element found"));
            target.click();
        }
       System.out.println("I AM A FIX");
       System.out.println("I AM A CHANGE MEANWHILE");
   }

}
