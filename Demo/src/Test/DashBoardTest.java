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
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class DashBoardTest {
    private WebDriver driver;
    private static String dashboardUrl = "https://happy-field-011ab7b10.2.azurestaticapps.net/dashboard.html";
    @BeforeClass
    public void setup(){
        System.setProperty("webDriver.chrome.driver","Desktop/Demo/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // maximize window when it opens in chrome
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS); // driver should wait for 5 seconds when se
    }

    @Test
    public void verifyPaymentApprovals(){
        driver.get(dashboardUrl);
        WebElement paymentApprovals = driver.findElement(By.xpath("//*[@id=\"viewmodels-user-dashboard\"]/div[2]/div[1]/div[2]/ul/li[2]/a"));
        paymentApprovals.click();

        WebElement paymentStatement = driver.findElement(By.xpath("//*[@id=\"viewmodels-user-dashboard\"]/div[2]/div[2]/div[3]/div[2]/div[1]/p/text()"));

        Assert.assertTrue(paymentStatement.isDisplayed());
    }

    @Test
    public void verifyPendingPayments(){
        driver.get(dashboardUrl);
        WebElement pendingPayments = driver.findElement(By.xpath("//*[@id=\"viewmodels-user-dashboard\"]/div[2]/div[1]/div[2]/ul/li[3]/a"));
        pendingPayments.click();

        WebElement currentPaymentsStatement = driver.findElement(By.xpath("//*[@id=\"viewmodels-user-dashboard\"]/div[2]/div[2]/div[4]/div[3]/div[1]/h3"));

        Assert.assertTrue(currentPaymentsStatement.isDisplayed());
    }


    @AfterClass
    public void clean(){
        driver.quit(); //quit driver and close all associated window
        driver.close();
    }

}

