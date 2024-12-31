package com.example;

import com.example.model.User;
import com.example.service.UserCreator;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public  class AbstractPage {

    protected WebDriver driver;
    protected WebDriverWait wait;
    private Logger logger = LoggerFactory.getLogger(AbstractPage.class);

    public AbstractPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @FindBy(how = How.XPATH, using = "//body")
    WebElement body;
    @FindBy(how = How.ID, using = "login-username")
    WebElement userName;
    @FindBy(how = How.ID, using =  "login-signin")
    WebElement loginButton;
    @FindBy(how = How.ID, using =  "login-passwd")
    WebElement loginPasswd;
    @FindBy(how = How.XPATH, using = "//button[@name='verifyPassword']")
    WebElement verifyPassword;


    public void fillLoginName(String username){
        try {
            waitForElementToBeVisible(userName);
            userName.sendKeys(username);
        } catch (Exception e) {
            logger.error("Error while filling the login name: {}", e.getMessage(), e);
            throw e;
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
        } catch (Exception e) {
            logger.error("Error while clicking the login button: {}", e.getMessage(), e);
            throw e;
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
        } catch (Exception e) {
            logger.error("Error while filling the password: {}", e.getMessage(), e);
            throw e;
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
        logger.info("Logged in with email: " + user.getEmail());
    }

    public boolean login(String name, String password){
        try {
            fillLoginName(name);
            loginButton.click();
            logger.info("Login button clicked successfully.");

            fillPassword(password);
            verifyPassword.click();
            logger.info("Login process completed successfully.");
            return true;
        } catch (Exception e) {
            logger.error("Error during login: {}", e.getMessage(), e);
            throw e;
        }
    }

    protected WebElement waitForElementToBeVisible(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            logger.warn("Element not visible within timeout: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while waiting for element to be visible: {}", e.getMessage(), e);
            throw e;
        }
    }
    protected WebElement waitForElementToBeClickable(WebElement element) {
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            logger.warn("Element not clickable within timeout: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error while waiting for element to be clickable: {}", e.getMessage(), e);
            throw e;
        }
    }
}
