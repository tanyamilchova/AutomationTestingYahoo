package com.example.page;

import com.example.util.Util;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailPageDecorator extends MailPage {

    protected final MailPage mailPage;
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MailPageDecorator(MailPage mailPage) {
        super(mailPage.driver);
        this.mailPage = mailPage;
    }

    private static final String CC_BUTTON_LOCATOR = "//button[@data-test-id = 'btn-cc']";
    private static final String CC_FIELD_LOCATOR = "message-cc-field";

    @FindBy(how = How.XPATH, using = CC_BUTTON_LOCATOR)
    public WebElement ccButton;

    @FindBy(how = How.ID, using = CC_FIELD_LOCATOR)
    public WebElement ccField;

    @Override
    public void createEmail(String sendToField, String subject, String text) {
        mailPage.createEmail(sendToField, subject, text);
    }

    /**
     * Creates and sends an email with an optional CC recipient.
     *
     * @param sendToField The recipient's email address.
     * @param subject The subject of the email.
     * @param text The body of the email.
     * @param copyTo The email address for CC (copy to).
     * @throws RuntimeException if the email creation process fails.
     *
     * This method validates the CC address, fills in the email details,
     * and sends the email. Logs an error if any step fails.
     */
    public void createEmailWithCC(String sendToField, String subject, String text, String copyTo) {
        try {
            Util.validateProperty(copyTo);

            createEmail(sendToField, subject, text);
            clickElement(ccButton);

            waitForElementToBeVisible(ccField);
            enterText(ccField, copyTo);

            mailPage.sendEmail();
        } catch (NoSuchElementException exception) {
            logger.error("Failed to locate an element while creating email with CC: {}", exception.getMessage());
            throw new RuntimeException("Element not found during email creation", exception);
        } catch (TimeoutException exception) {
            logger.error("Timed out while interacting with elements for email with CC: {}", exception.getMessage());
            throw new RuntimeException("Timeout occurred during email creation", exception);
        } catch (StaleElementReferenceException e) {
            logger.warn("Stale element reference occurred.");

        } catch (Exception exception) {
            logger.error("An unexpected error occurred: {}", exception.getMessage());
            throw new RuntimeException("Failed to create email with CC", exception);
        }
    }
    private void clickElement(WebElement element) {
        element.click();
    }
    private void enterText(WebElement element, String text) {
        element.click();
        element.sendKeys(text);
    }
}
