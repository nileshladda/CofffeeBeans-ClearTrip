package clearT.automation.com;

import org.testng.annotations.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



public class ClearTripService {
    public static LandingPage landing = new LandingPage();
    public static BookingPage booking = new BookingPage();
    @Test
    public void clearTripStartChrome() throws Exception{
        System.setProperty("webdriver.chrome.driver", "RequiredDrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        landing.PageExecution(driver);
        booking.PageExecution(driver);

    }

    @Test
    public void clearTripStartFirefox() throws Exception{
        System.setProperty("webdriver.gecko.driver", "RequiredDrivers\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver();
        landing.PageExecution(driver);
        booking.PageExecution(driver);

    }

}

