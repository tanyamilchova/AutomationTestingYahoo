package com.example.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends AbstractPage {
    private static final Logger logger = LoggerFactory.getLogger(HomePage.class);


    public HomePage(WebDriver driver) {
        super(driver);
    }

    private static final String SIGN_IN_BUTTON_LOCATOR = "#login-container a";
    private static final String SCROLL_DOWN_BUTTON_LOCATOR = "scroll-down-btn";
    private static final String AGREE_BUTTON_LOCATOR = "//button[text()='Приемане на всички']";
    private static final String WRONG_EMAIL_MESSAGE_ACCOUNT_LOCATOR = "//div[@class='writeup sml-txt description']/p";


    @FindBy(how = How.CSS, using = SIGN_IN_BUTTON_LOCATOR)
    public WebElement signInButton;

    @FindBy(how = How.ID, using = SCROLL_DOWN_BUTTON_LOCATOR)
    public WebElement scrollDownButton;

    @FindBy(how = How.XPATH, using = AGREE_BUTTON_LOCATOR)
    public WebElement agree;

    @FindBy(how = How.XPATH, using = WRONG_EMAIL_MESSAGE_ACCOUNT_LOCATOR)
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
            waitForElementToBeVisible(scrollDownButton);
            scrollDownButton.click();

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

