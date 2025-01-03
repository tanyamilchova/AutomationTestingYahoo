package com.example.page;

import com.example.util.Util;
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

    @FindBy(how = How.XPATH, using = "//button[@data-test-id = 'btn-cc']")
    WebElement ccButton;
    @FindBy(how = How.XPATH, using = "//input[@id = 'message-cc-field']")
    WebElement ccField;

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
    public void createEmail(String sendToField, String subject, String text, String copyTo) {
        try {
            Util.validateProperty(copyTo);

            createEmail(sendToField, subject, text);
            ccButton.click();

            waitForElementToBeVisible(ccField);
            ccField.click();
            ccField.sendKeys(copyTo);

            mailPage.sendEmail();
        }catch (Exception e){
        logger.error("An error occurred while creating the email with copy to... : {}", e.getMessage());
        throw new RuntimeException("Failed to create the email with copy to... ", e);
        }
    }
}
