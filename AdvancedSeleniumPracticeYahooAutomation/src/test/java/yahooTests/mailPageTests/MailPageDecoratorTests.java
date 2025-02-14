package yahooTests.mailPageTests;

import com.example.page.MailPage;
import com.example.page.MailPageDecorator;
import com.example.page.PageFactory;
import com.example.model.User;
import com.example.service.TestDataReader;
import com.example.service.UserCreator;
import com.example.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yahooTests.AbstractPageTest;
import static org.testng.AssertJUnit.assertEquals;

    /**
     * Test class for MailPageDecorator functionalities.
     * <p>
     * This class contains test method to verify the creation of new email with functionality to send copy to another email
     * </p>
     *
     * @see MailPage
     * @see AbstractPageTest
     */
    public class MailPageDecoratorTests extends MailPageTest{
        MailPageDecorator decoratedPage;
        private  final Logger logger = LogManager.getLogger(yahooTests.mailPageTests.MailPageDecoratorTests.class);

        /**
         * Sets up the test environment before each test method.
         * <p>
         * This includes invoking super.setup(), navigating to the mail decorator page
         * </p>
         */
        @BeforeMethod
        public void setup() {
            try {
                logger.info("Setting up test environment...");
                super.setup();

                String mailPageCopyToDecorator = Util.validateProperty(TestDataReader.getTestData("page.mail_copy_to_decorator"));
                decoratedPage = (MailPageDecorator) PageFactory.getPage(driver, mailPageCopyToDecorator);
            } catch (Exception e) {
                logger.error("Error occurred during setup: {}", e.getMessage(),e);
                throw e;
            }
        }

        /**
         * Tests the creation of a new email with added copyTo functionality
         * <p>
         * This method verifies that an email can be created with the specified
         * recipient, subject, text content, and copyTo email.
         * </p>
         */
        @Test
        public void createNewEmailWithCopyTo() {
            try {
                logger.info("Testing creation of new email...");
                User user = UserCreator.withEmailAttributesAndSendToCopy();

                int sentLetters = decoratedPage.getNumberOfSentLetters();
                decoratedPage.createEmailWithCC(user.getSendToEmail(), user.getEmailSubject(), user.getEmailText(), user.getSendToCopyEmail());
                logger.debug("Email created with recipient: " + user.getSendToEmail() + ", subject: " + user.getEmailSubject());

                decoratedPage.refreshBox(decoratedPage.getSentFolderElement());
                int currentSentLetters = decoratedPage.getNumberOfSentLetters();
                assertEquals((sentLetters + 1), currentSentLetters);

                logger.info("New email sent successfully. Sent letters count: " + currentSentLetters);
            } catch (NoSuchElementException e) {
                logger.error("Error occurred while interacting with the email elements: {}", e.getMessage(), e);
                throw e;
            } catch (AssertionError e) {
                logger.error("Assertion failed during email creation test: {}", e.getMessage(), e);
                throw e;
            } catch (Exception e) {
                logger.error("Unexpected error occurred during the test execution: {}", e.getMessage(), e);
                throw new RuntimeException("Test execution failed due to unexpected error", e);
            }
        }
}
