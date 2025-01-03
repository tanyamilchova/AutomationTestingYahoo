package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;

public class HomePage extends AbstractPage {
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
    @FindBy(how = How.XPATH, using ="//div[@class='writeup sml-txt description']/p")
    public WebElement wrongEmailMessageAccount;


    /**
     * This method performs the sign-in process by interacting with the necessary elements
     * on the web page. It waits for the visibility of various elements such as the
     * "scroll down" button, "agree" button, and "sign-in" button, clicking them in sequence
     * to complete the sign-in process.
     *
     * It also logs the process at different levels (info, debug, error) to track the
     * progress and handle any errors that may occur during the sign-in procedure.
     *
     * @throws Exception if an error occurs during the sign-in process, the method throws
     *         the exception for further handling.
     */
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
    public String getWrongEmailMessage(){
        return wrongEmailMessageAccount.getText();
    }
}
