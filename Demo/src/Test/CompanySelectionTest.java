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

public class CompanySelectionTest {

    private WebDriver driver;

    @BeforeClass
    public void setup(){

        System.setProperty("webDriver.chrome.driver","Desktop/Demo/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // maximize window when it opens in chrome
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS); // driver should wait for 5 seconds when se
    }

    @Test
    public void verifySearchBar(){


        String searchName = "Dream home Improvements";
        WebElement searchBar = driver.findElement(By.id("search-area-accounting-firms"));

        searchBar.click();
        searchBar.sendKeys(searchName);
        searchBar.sendKeys(Keys.ENTER);

        String expectedCompanyName = driver.findElement(By.xpath("//*[@id=\"components-user-companies-selectCompanyAccountantDashboard\"]/div/div/div[3]/div[1]/div/table/tbody/tr[1]/td[1]/span")).getText();

        Assert.assertEquals(expectedCompanyName,searchName);

    }

    @Test
    public void verifyAddClientButton(){

        WebElement addButton = driver.findElement(By.xpath("//*[@id=\"components-user-companies-selectCompanyAccountantDashboard\"]/div/div/div[1]/div[2]/button"));
        addButton.click();
        String expectedTitle = "Plooto | Add Client";
        String actualTitle = driver.getTitle();

        Assert.assertEquals("Title matches",expectedTitle,actualTitle);
    }

    @Test
    public void verifyPlootInc(){
        WebElement plootoInc = driver.findElement(By.xpath("//*[@id=\"viewmodels-user-company-select\"]/div/div/div/div[2]/div/div[1]/ul[2]/li[1]/a/span"));
        plootoInc.click();
        String expectedTitle = "Dashboard | Plooto";
        String actualTitle = driver.getTitle();

        Assert.assertEquals("Title matches",expectedTitle,actualTitle);
    }


    @AfterClass
    public void clean(){
        driver.quit(); //quit driver and close all associated window
        driver.close();
    }
}
