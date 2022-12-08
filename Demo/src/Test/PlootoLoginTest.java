package Test;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PlootoLoginTest {

    private WebDriver driver; // webDriver object
    private Logger logger;
    private static String loginUrl = "https://happy-field-011ab7b10.2.azurestaticapps.net/login.html"; // any url login page is using
    private static String validEmail = "yohannes@gmail.com";
    private static  String validPassword = "D!0aegk";
    private static String invalidEmail = "KSI.coooom";
    private static String invalidPassword = "ABCD2DDDD";
    private static Integer minimum = 8; //minimum password length
    private static Integer maximum  = 12; //maximum password length
    @BeforeClass
    public void setup(){
        logger = Logger.getLogger(PlootoLoginTest.class.getName());
        System.setProperty("webDriver.chrome.driver","Desktop/Demo/chromedriver");
        driver = new ChromeDriver();
        driver.manage().window().maximize(); // maximize window when it opens in chrome
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(5,TimeUnit.SECONDS); // driver should wait for 5 seconds when searching for an element
    }


    @Test
    public void verifyLoginPage(){

        driver.get(loginUrl);

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("passphrase"));
        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"components-signin\"]/form/fieldset/div[3]/a"));

        email.clear();
        System.out.println("Entering email");
        email.sendKeys(this.validEmail);

        password.clear();
        System.out.println("entering the password");
        password.sendKeys(this.validPassword);

        System.out.println("Clicking login button");
        signInButton.click();

        String expectedTitle = "Select Your Company | Plooto";
        String actualTitle = driver.getTitle();

        logger.info("Verifying the page title has started");
        Assert.assertEquals("Page title matches", expectedTitle, actualTitle);

        logger.info("The page title is successfully verified");

        logger.info("User has been logged successfully");

    }

    @Test
    public void verifyMaskedPassword(){
        driver.get(loginUrl);
        WebElement password = driver.findElement(By.id("passphrase"));
        password.clear();
        Assert.assertEquals("password field is masked", "password", password.getAttribute("type"));
    }

    @Test
    //Assuming password has specific minimum and max length specified
    public void verifyPasswordLength(){
        driver.get(loginUrl);
        WebElement password = driver.findElement(By.id("passphrase"));
        password.clear();
        if(password.getAttribute("maxLength") == null) {
            logger.setLevel(Level.WARNING);
            logger.warning("Password maxlength field not set");
        }else if(password.getAttribute("minLength") == null){
            logger.setLevel(Level.WARNING);
            logger.warning("Password minlength field not set");
        }else{
            Assert.assertEquals("password max length is set", password.getAttribute("maxLength"), this.maximum);
            Assert.assertEquals("password min length is set", password.getAttribute("minLength"), this.minimum);
        }

    }

    @Test
    public void verifyInvalidMessage(){
        driver.get(loginUrl);
        WebElement email = driver.findElement(By.id("email"));
        email.clear();
        System.out.println("Entering email");
        email.sendKeys(this.invalidEmail);
        String emailRegex = "";
        WebElement password = driver.findElement(By.id("passphrase"));
        password.clear();
        System.out.println("entering the password");
        password.sendKeys(this.invalidPassword);

        WebElement loginButton = driver.findElement(By.id("login-button"));
        System.out.println("Clicking login button");
        loginButton.click();

        String passwordRegex = "";
        Pattern emailPattern = null; // Pattern object for email
        Pattern passwordPattern = null; //Pattern object for password


        if (email.getAttribute("pattern") == null) {
            emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."
                    + "[a-zA-Z0-9_+&*-]+)*@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            emailPattern = Pattern.compile(emailRegex);
            logger.setLevel(Level.WARNING);
            logger.warning("email pattern field not set");
        }else if(password.getAttribute("pattern") == null) {
            passwordRegex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,12}$"; //assuming password length between 8-12 and following standard password specifications
            passwordPattern = Pattern.compile(passwordRegex);
            logger.setLevel(Level.WARNING);
            logger.warning("email pattern field not set");
        }else{
            emailPattern = Pattern.compile(email.getAttribute("pattern"));
            passwordPattern = Pattern.compile(password.getAttribute(("pattern")));
        }

        String expectedMessage = "You have entered an invalid email or password";
        String actualValidationMessage = driver.findElement(By.id("login-error")).getText();


        if(actualValidationMessage == null){
            logger.setLevel(Level.WARNING);
            logger.warning("password and email validation error Message field is not set");
        }
        else if (!(emailPattern.matcher(invalidEmail).matches()) || !(passwordPattern.matcher(invalidPassword).matches())){
            Assert.assertTrue(expectedMessage.equalsIgnoreCase(actualValidationMessage));
        }

    }

    @Test
    public void verifyForgotPassword(){
        driver.get(loginUrl);
        WebElement forgotPassword = driver.findElement(By.xpath("//*[@id='components-signin']/form/fieldset/div[2]/div[1]/a"));
        forgotPassword.click();

        String actualTitle = driver.getTitle();
        String expectedTitle = "Forgot Password Page"; // assuming this is the title of page opened

        Assert.assertEquals("Page title matches", expectedTitle, actualTitle);

    }


    @AfterClass
    public void clean(){
        driver.quit(); //quit driver and close all associated window
        driver.close();
    }


}