package Test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.sql.Driver;
import java.util.concurrent.TimeUnit;
public class PendingPaymentsTest {

    private WebDriver driver;
    private static String currentUrl = "https://happy-field-011ab7b10.2.azurestaticapps.net/pending_payments.html";
    @BeforeClass
    public void setup(){
        System.setProperty("webDriver.chrome.driver","Desktop/Demo/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // maximize window when it opens in chrome
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS); // driver should wait for 5 seconds when se
    }

    @Test
    public void verifyCavagesLink(){
        driver.get(currentUrl);
        WebElement cavages = driver.findElement(By.xpath("//*[@id=\"components-user-payments-tables-transactionsTable\"]/table/tbody[2]/tr[5]/td[1]/span"));
        cavages.click();
        String expectedPageTitle = "View Payment | Plooto";
        String actualPageTitle = driver.getTitle();

        Assert.assertEquals("Link navigates to the right page",expectedPageTitle,actualPageTitle);
    }

    @Test
    public void verifyActivePendingReceivables(){
        driver.get(currentUrl);
        WebElement pendingReceivables = driver.findElement(By.xpath("//*[@id=\"viewmodels-user-dashboard\"]/div[2]/div[2]/div[4]/div[3]/ul/li[2]/a"));
        pendingReceivables.click();
        Assert.assertTrue(pendingReceivables.getAttribute("class").equals("active"));
    }

    @Test
    public void verifyNavigationBack(){
        driver.get(currentUrl);
        WebElement cavages = driver.findElement(By.xpath("//*[@id=\"components-user-payments-tables-transactionsTable\"]/table/tbody[2]/tr[5]/td[1]/span"));
        cavages.click();
        driver.navigate().back(); //click back on the web browser
        String expectedTitlePage = "DashBoard | Plooto";
        String actualTitlePage = driver.getTitle();

        Assert.assertEquals("Page navigates to the right place", expectedTitlePage, actualTitlePage);

    }

    @Test void verifyDashboardTab(){
        driver.get(currentUrl);
        WebElement dashboard = driver.findElement(By.xpath("//*[@id=\"nav-top-left\"]/li[1]/a"));
        dashboard.click();
        String expectTitlePage = "Dashboard | Plooto";
        String actualTitlePage = driver.getTitle();

        Assert.assertEquals("Dashboard tab navigates website to the right page", expectTitlePage, actualTitlePage);
    }
}
