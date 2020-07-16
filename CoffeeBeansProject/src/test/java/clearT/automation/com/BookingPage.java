package clearT.automation.com;

import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;

public class BookingPage {

    static int PriceOut=0, PriceIn=0, BillT=0, OutgoingBelow5k= 0, IncomingBelow7k=0 , OutgoingFilterBelow7k=0 ,IncomingFilterBelow7k=0,payable=0;

    static By numberOfDepartureFlights = By.xpath("//div[@data-test-attrib=\"onward-view\"]//p[@class=\"m-0 fs-4 fw-600 c-neutral-900\"]");
    static By numberOfReturnFlights = By.xpath("//div[@data-test-attrib=\"return-view\"]//p[@class=\"m-0 fs-4 fw-600 c-neutral-900\"]");
    static By nonStop = By.xpath("//label[@class=\"checkbox w-100p checkbox flex-1 bs-border w-100p br-4 h-7 px-1 hover:bg-neutral-0\"]//p[text()=\"Non-stop\"]");
    static By oneStop = By.xpath("//label[@class=\"checkbox w-100p checkbox flex-1 bs-border w-100p br-4 h-7 px-1 hover:bg-neutral-0\"]//p[text()=\"Non-stop\"]");
    static By selectingDepartureFlight =By.xpath("//div[@data-test-attrib=\"onward-view\"]//div[@class=\"ba bc-neutral-100 br-4 px-3 rt-tuple-container tp-box-shadow td-200 hover:elevation-3 c-pointer\"]");
    static By selectingReturnFlight = By.xpath("//div[@data-test-attrib=\"return-view\"]//div[@class=\"ba bc-neutral-100 br-4 px-3 rt-tuple-container tp-box-shadow td-200 hover:elevation-3 c-pointer\"]");
    static By fareOfDepartureFlight=By.xpath("//div[@data-test-attrib=\"onward-view\"]//p[@class=\"m-0 fs-4 fw-600 c-neutral-900\"]");
    static By fareOfReturnFlight= By.xpath("//div[@data-test-attrib=\"return-view\"]//p[@class=\"m-0 fs-4 fw-600 c-neutral-900\"]");
    static By totalPaidAmmount=By.xpath("//div[@class=\"sticky__parent\"]//span[@class=\"c-neutral-900 mx-4  fw-700 flex flex-right fs-7\"]");

    public static void PageExecution(WebDriver driver) throws Exception{

        countBefore(driver);
        applyFilter(driver);
        countAfter(driver);
        selectingFlight(driver);
        fetchingFare(driver);
        Bill(driver);
        checking();
        breakPage(driver);
    }


    public static void countBefore (WebDriver driver){
        // counting number of departure flights before filter below 5000
        List<WebElement> elements = driver.findElements(numberOfDepartureFlights);
        for (WebElement s : elements) {
            if (fare(s.getText()) < 5000) {
                ++OutgoingBelow5k;
            } else break;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        //counting number of return flights before filter below 7000
        List<WebElement> elements2 = driver.findElements(numberOfReturnFlights);
        for (WebElement s1 : elements2) {
            if (fare(s1.getText())  < 7000) {
                ++IncomingBelow7k;
            } else break;
        }
        System.out.println("Total number of outgoing flight below 5000 before filter: " + OutgoingBelow5k);
        System.out.println("Total number of incoming flight below 7000 before filter: " + IncomingBelow7k);
    }
    public static void applyFilter (WebDriver driver)throws Exception{
        // Label: selecting filters
        Thread.sleep(3000);
        driver.findElement(nonStop).click();
        Thread.sleep(3000);
        driver.findElement(oneStop).click();
    }
    public static void countAfter (WebDriver driver){
        // counting number of flight after filter
        List<WebElement> elements_filter = driver.findElements(numberOfDepartureFlights);
        for (WebElement s : elements_filter) {
            String k = s.getText();
            k = k.replace("₹", "");
            k = k.replace(",", "");
            if (Integer.parseInt(k) < 7100) {
                ++OutgoingFilterBelow7k;
            } else break;
        }
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        List<WebElement> elements1_filter = driver.findElements(numberOfReturnFlights);
        for (WebElement s1 : elements1_filter) {
            String k = s1.getText();
            k = k.replace("₹", "");
            k = k.replace(",", "");
            if (Integer.parseInt(k) < 7000) {
                ++IncomingFilterBelow7k;
            } else break;
        }
        System.out.println("Total number of flight below 7000 after filter: " + (IncomingFilterBelow7k + OutgoingFilterBelow7k));
    }
    public static void selectingFlight (WebDriver driver){
        //selecting flights
        List<WebElement> flight_select1 = driver.findElements(selectingDepartureFlight);
        flight_select1.get(0).click();
        List<WebElement> flight_select2 = driver.findElements(selectingReturnFlight);
        flight_select2.get(3).click();
    }
    public static void fetchingFare (WebDriver driver){
        //fetching fares
        List<WebElement> fare1 = driver.findElements(fareOfDepartureFlight);
        String outgoing_fare = fare1.get(1).getText();
        outgoing_fare = outgoing_fare.replace("₹", "");
        outgoing_fare = outgoing_fare.replace(",", "");
        PriceOut = Integer.parseInt(outgoing_fare);
        List<WebElement> fare2 = driver.findElements(fareOfReturnFlight);
        String incoming_fare = fare2.get(4).getText();
        incoming_fare = incoming_fare.replace("₹", "");
        incoming_fare = incoming_fare.replace(",", "");
        PriceIn = Integer.parseInt(incoming_fare);
    }
    public static void Bill (WebDriver driver){
        //fetching total amount to be paid
        BillT = fare(driver.findElement(totalPaidAmmount).getText());
        payable = PriceOut + PriceIn;
        System.out.println("Amount to pay: " + PriceOut + " + " + PriceIn + " = " + payable);
    }
    public static void checking ()throws Exception{
        if (payable == BillT) {
            System.out.println("All are correct");
        } else {
            System.out.println("something wrong");
        }
        Thread.sleep(10000);
    }
    public static int fare(String Fare){
        Fare = Fare.replace("₹", "");
        Fare = Fare.replace(",", "");
        return Integer.parseInt(Fare);
    }
    public static void breakPage(WebDriver driver){
        driver.quit();

    }
}
