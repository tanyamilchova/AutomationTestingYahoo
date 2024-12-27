package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class HomePage extends AbstractPage{
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);
    protected WebDriverWait wait;

    public HomePage(WebDriver driver) {
        super(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(7));
    }
    @FindBy(how = How.CSS, using = "#login-container a")
    public WebElement signInButton;
    @FindBy(how = How.ID, using = "scroll-down-btn")
    public WebElement scrowDownButton;
    @FindBy(how = How.XPATH, using ="//button[text()='Приемане на всички']")
    public WebElement agree;
    @FindBy(how = How.CSS, using ="button._yb_bee2q[tabindex=\"-1\"]")
    public WebElement mailButton;

    public void signIn() {
        try {
            logger.info("Starting sign-in process.");

            logger.debug("Waiting for scroll down button to be visible.");
            waitForElementToBeVisible(scrowDownButton);
            scrowDownButton.click();

            logger.debug("Waiting for agree button to be visible.");
            waitForElementToBeVisible(agree);
            agree.click();

            logger.debug("Waiting for sign-in button to be visible.");
            waitForElementToBeVisible(signInButton);
            signInButton.click();

            logger.info("Sign-in process completed successfully.");
        } catch (Exception e) {
            logger.error("Error occurred during sign-in process: " + e.getMessage(), e);
            throw e;
        }
    }
}
