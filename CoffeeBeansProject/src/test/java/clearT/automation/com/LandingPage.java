package clearT.automation.com;

import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import java.time.LocalDate;

public class LandingPage {

    static int PriceOut=0, PriceIn=0, BillT=0, OutgoingBelow5k= 0, IncomingBelow7k=0 , OutgoingFilterBelow7k=0 ,IncomingFilterBelow7k=0;

    static int payable;
    static By findFlightIcon =By.xpath("//*[@id=\"Home\"]/div/aside[1]/nav/ul[1]/li[1]/a[1]");
    static By selectingRoundTrip = By.xpath("//*[@id=\"SearchForm\"]/nav/ul/li[2]/label");
    static By source = By.id("FromTag");
    static By destination = By.id("ToTag");
    static By departureDate=By.cssSelector("#DepartDate");
    static By returnDate = By.cssSelector("#ReturnDate");
    static By findFlight = By.xpath("//*[@id=\"SearchBtn\"]");

    public static void PageExecution(WebDriver driver) throws Exception{
        openingUrl(driver);
        roundTrip(driver);
        locations(driver);
        selectingDates(driver);
        searchFlight(driver);
    }

    public static void openingUrl (WebDriver driver) {
        driver.get("https://www.cleartrip.com/");
        driver.manage().window().maximize();
    }
    public static void roundTrip (WebDriver driver) throws Exception{
        // Label: selecting flights
        driver.findElement(findFlightIcon).click();
        Thread.sleep(3000);
        // Label: selecting round trip
        driver.findElement(selectingRoundTrip).click();
        Thread.sleep(3000);
    }
    public static void locations (WebDriver driver)throws Exception{
        // giving source and destination
        driver.findElement(source).click();
        driver.findElement(source).sendKeys("Delhi");
        Thread.sleep(5000);
        driver.findElement(source).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(destination).click();
        driver.findElement(destination).sendKeys("Mumbai");
        Thread.sleep(5000);
        driver.findElement(destination).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
    }
    public static void selectingDates (WebDriver driver)throws Exception{
        // Label: selecting dates
        LocalDate dt = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM, YYYY");
        driver.findElement(departureDate).clear();
        driver.findElement(departureDate).sendKeys(formatter.format(dt).toString());
        Thread.sleep(3000);
        driver.findElement(departureDate).click();
        dt = dt.plusDays(10);
        driver.findElement(returnDate).clear();
        driver.findElement(returnDate).sendKeys(formatter.format(dt).toString());
        Thread.sleep(3000);
        driver.findElement(returnDate).click();
    }
    public static void searchFlight (WebDriver driver)throws Exception{
        //clicking on search button
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(findFlight).click();
        Thread.sleep(3000);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }
    public static int fare(String Fare){
        Fare = Fare.replace("₹", "");
        Fare = Fare.replace(",", "");
        return Integer.parseInt(Fare);
    }
}
