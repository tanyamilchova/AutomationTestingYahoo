package com.example.page;

import com.example.driver.FluentWaitImpementation;
import com.example.exceptions.PropertyException;
import com.example.model.User;
import com.example.service.TestDataReader;
import com.example.service.UserCreator;
import com.example.util.Util;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public abstract class AbstractPage {

    protected WebDriver driver;
    protected FluentWaitImpementation waitFluent;
    private Logger logger = LoggerFactory.getLogger(AbstractPage.class);
    private String waitFluentTime = TestDataReader.getTestData("wait.fluent.time");


    public AbstractPage(WebDriver driver){
        this.driver = driver;
        int waitTime = Util.getTime(waitFluentTime);
        waitFluent = new FluentWaitImpementation(driver, Duration.ofSeconds(waitTime));
        PageFactory.initElements(driver, this);
    }



    private static final String BODY_LOCATOR = "//body";
    private static final String USERNAME_LOCATOR = "login-username";
    private static final String LOGIN_BUTTON_LOCATOR = "login-signin";
    private static final String LOGIN_PASSWD_LOCATOR = "login-passwd";
    private static final String VERIFY_PASSWORD_LOCATOR = "//button[@name='verifyPassword']";

    @FindBy(how = How.XPATH, using = BODY_LOCATOR)
    WebElement body;

    @FindBy(how = How.ID, using = USERNAME_LOCATOR)
    WebElement userName;

    @FindBy(how = How.ID, using = LOGIN_BUTTON_LOCATOR)
    WebElement loginButton;

    @FindBy(how = How.ID, using = LOGIN_PASSWD_LOCATOR)
    WebElement loginPasswd;

    @FindBy(how = How.XPATH, using = VERIFY_PASSWORD_LOCATOR)
    WebElement verifyPassword;

    public void fillLoginName(String username){
        try {
            waitForElementToBeVisible(userName);
            userName.sendKeys(username);
        } catch (Exception exception) {
            logger.error("Error while filling the login name: {}", exception.getMessage(), exception);
            throw exception;
        }
    }
    public String getLoginNameValue(){
        try {
            return userName.getAttribute("value");
        } catch (Exception e) {
            logger.error("Error while retrieving the login name value: {}", e.getMessage(), e);
            return null;
        }
    }


    public AbstractPage nameFormClickNext(){
        try {
            loginButton.click();
        } catch (Exception exception) {
            logger.error("Error while clicking the login button: {}", exception.getMessage(), exception);
            throw exception;
        }
        return this;
    }

    public AbstractPage passwordFormClickNext(){
        try {
            verifyPassword.click();
        } catch (Exception e) {
            logger.error("Error while clicking the verify password button: {}", e.getMessage(), e);
            throw e;
        }
        return this;
    }

    public AbstractPage fillPassword(String password){
        try {
            waitForElementToBeVisible(loginPasswd);
            loginPasswd.clear();
            loginPasswd.sendKeys(password);
        } catch (Exception exception) {
            logger.error("Error while filling the password: {}", exception.getMessage(), exception);
            throw exception;
        }
        return this;
    }

    public String getPasswordValue(){
        try {
            return loginPasswd.getAttribute("value");
        } catch (Exception e) {
            logger.error("Error while retrieving the password value: {}", e.getMessage(), e);
            return null;
        }
    }
    public  void loginUser(){
        User user = UserCreator.withValidEmailAndPasswordFromEnvironment();
        login(user.getEmail(), user.getPassword());
        logger.info("Logging user with  with email: " + user.getEmail());
    }

    public boolean login(String name, String password){
        try {
            fillLoginName(name);
            loginButton.click();
            logger.info("Login button clicked successfully for {}.", userName);

            fillPassword(password);
            verifyPassword.click();
            logger.info("Login process completed successfully.");
            return true;
        } catch (Exception exception) {
            logger.error("Error during login: {}", exception.getMessage(), exception);
            throw exception;
        }
    }

    protected void waitForElementToBeVisible(WebElement element) {
        waitFluent.waitForElement(element);
    }

    protected void waitForElementToBeClickable(WebElement element) {
        waitFluent.waitForElement(element);
    }
}
