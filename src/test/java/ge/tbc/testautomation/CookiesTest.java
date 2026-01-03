package ge.tbc.testautomation;
import ge.tbc.testautomation.data.Constants;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.Set;

public class CookiesTest {
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
    }

    @Test(description = "filter cookies", priority = 1)
    public void filterCookies() {
        driver.get(Constants.TECHLISTIC_URL);
        Cookie activeTemplateCookie = driver.manage().getCookies().stream()
                .filter(cookie -> cookie.getName().contains(Constants.ACTIVE_TEMPLATE_COOKIE))
                .findFirst()
                .orElse(null);
        Assert.assertTrue(activeTemplateCookie.getValue().contains(Constants.PUB_SITE));
    }

    @Test(description = "inject Cookie", priority = 2)
    public void injectCookie() {
        driver.get(Constants.TECHLISTIC_URL);

        for (int i = 1; i <= 10; i++) {
            Cookie cookie = new Cookie("cookieName" + i, "Value" + i);
            driver.manage().addCookie(cookie);
        }

        Set<Cookie> cookies = driver.manage().getCookies();

        for (Cookie cookie : cookies) {
            System.out.println(cookie.getName() +" "+ cookie.getValue());
        }
        for (int i = 1; i <= 10; i++) {
            driver.manage().deleteCookieNamed("cookieName" + i);
        }
        Set<Cookie> cookiesAfterDelete = driver.manage().getCookies();
        Assert.assertFalse(cookiesAfterDelete.stream().anyMatch(c -> c.getName().startsWith("cookieName")));
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
