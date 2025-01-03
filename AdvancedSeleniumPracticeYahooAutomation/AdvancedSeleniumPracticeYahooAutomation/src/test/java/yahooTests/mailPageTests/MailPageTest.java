package yahooTests.mailPageTests;

import com.example.page.MailPage;
import com.example.page.PageFactory;
import com.example.model.User;
import com.example.service.TestDataReader;
import com.example.service.URLCreator;
import com.example.service.UserCreator;
import com.example.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yahooTests.AbstractPageTest;

import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class for MailPage functionalities.
 * <p>
 * This class contains test methods to verify the creation of new emails and drafts,
 * sending drafts, and logging out from the mail page.
 * </p>
 *
 * @see MailPage
 * @see AbstractPageTest
 */
public class MailPageTest extends AbstractPageTest {
    MailPage page;
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Sets up the test environment before each test method.
     * <p>
     * This includes initializing the WebDriver, navigating to the mail page,
     * and logging into the mail account.
     * </p>
     */
    @BeforeMethod
    public void setup() {
        try {
            logger.info("Setting up test environment...");
            super.setup();
            driver.get(URLCreator.getMailURLFromProperty());

            String mailPage = Util.validateProperty(TestDataReader.getTestData("page.mail"));
            page = (MailPage) PageFactory.getPage(driver, mailPage);

            page.loginUser();
        } catch (Exception e) {
            logger.error("Error occurred during setup: {}", e.getMessage(),e);
            throw e;
        }
    }

    /**
     * Tests the creation of a new email.
     * <p>
     * This method verifies that an email can be created with the specified
     * recipient, subject, and text content.
     * </p>
     */
    @Test
    public void createNewEmail() {
        try {
            logger.info("Testing creation of new email...");
            User user = UserCreator.withEmailAttributes();

            int sentLetters = page.getNumberOfSentLetters();
            page.createEmail(user.getSendToEmail(), user.getEmailSubject(), user.getEmailText());
            logger.debug("Email created with recipient: " + user.getSendToEmail() + ", subject: " + user.getEmailSubject());

            page.refreshBox(page.getSentFolderElement());
            int currentSentLetters = page.getNumberOfSentLetters();
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


    /**
     * Tests the creation of a new draft.
     * <p>
     * This method verifies that a draft can be created and that the number
     * of drafts increases by one after creation.
     * </p>
     */
    @Test
    public void createNewDraft() {
        try {
            logger.info("Testing creation of new draft...");
            User user = UserCreator.withEmailAttributes();

            int numberDrafts = page.getNumberOfDrafts();
            page.createDraft(user.getSendToEmail(), user.getEmailSubject(), user.getEmailText());
            logger.debug("Draft created with recipient: " + user.getSendToEmail() + ", subject: " + user.getEmailSubject());

            page.refreshDraftCheckboxBox();
            int currentNumberOfDrafts = page.getNumberOfDrafts();
            assertEquals((numberDrafts + 1), currentNumberOfDrafts);

            logger.info("New draft created successfully. Draft count: " + currentNumberOfDrafts);
        } catch (NoSuchElementException e) {
            logger.error("Error occurred while interacting with the draft elements: {}", e.getMessage(), e);
            throw e;
        } catch (AssertionError e) {
            logger.error("Assertion failed during draft creation test: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred during the test execution: {}", e.getMessage(), e);
            throw new RuntimeException("Test execution failed due to unexpected error", e);
        }
    }


    /**
     * Tests sending a draft email.
     * <p>
     * This method verifies that a draft can be sent and that the number
     * of drafts decreases by one after sending. It also checks that the
     * draft's recipient, subject, and text match the expected values.
     * </p>
     */
    @Test
    public void sendADraft() {
        try {
            logger.info("Testing sending of a draft email...");
            User user = UserCreator.withEmailAttributes();

            int numberDrafts = page.getNumberOfDrafts();
            page.openDraftToSend();

            String emailValue = page.getEmailValue();
            String subjectValue = page.getSubjectValue();
            String textValue = page.getTextValue();

            logger.debug("Checking draft values... email: " + emailValue + ", subject: " + subjectValue + ", text: " + textValue);

            assertEquals(user.getSendToEmail(), emailValue);
            assertEquals(user.getEmailSubject(), subjectValue);
            assertEquals(user.getEmailText(), textValue);

            page.sendLatestDraft();
            page.refreshBox(page.getDraftBoxElement());
            int currentNumberOfDrafts = page.getNumberOfDrafts();
            assertEquals((numberDrafts - 1), currentNumberOfDrafts);

            logger.info("Draft email sent successfully. Remaining drafts: " + currentNumberOfDrafts);
        } catch (NoSuchElementException e) {
            logger.error("Error occurred while interacting with the draft elements: {}", e.getMessage(), e);
            throw e;
        } catch (AssertionError e) {
            logger.error("Assertion failed during draft sending test: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred during the test execution: {}", e.getMessage(), e);
            throw new RuntimeException("Test execution failed due to unexpected error", e);
        }
    }


    /**
     * Tests logging out from the mail account.
     * <p>
     * This method verifies that after logging out, the current URL matches
     * the expected URL, indicating a successful logout.
     * </p>
     */
    @Test
    public void logOut() {
        try {
            logger.info("Testing logout...");
            final String EXPECTED_URL = URLCreator.getYahooURLFromProperty();
            page.exitAccount();

            String actual_URL = driver.getCurrentUrl();
            assertEquals(EXPECTED_URL, actual_URL);

            logger.info("Logout successful. Current URL: " + actual_URL);
        } catch (NoSuchElementException e) {
            logger.error("Error occurred while trying to log out or find the elements: {}", e.getMessage(), e);
            throw e;
        } catch (AssertionError e) {
            logger.error("Assertion failed during logout test. Expected URL: 'https://www.yahoo.com/', but found: {}", e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred during logout test execution: {}", e.getMessage(), e);
            throw new RuntimeException("Test execution failed due to unexpected error", e);
        }
    }

    /**
     * Test to verify that sending an email without a theme triggers the correct warning message.
     * This test checks the behavior when the subject field is left empty.
     */
    @Test
    public  void sendEmailWithoutTheme(){
        try {
            logger.info("Creating user with email attributes...");
            User user = UserCreator.withEmailAttributes();
            User userEmptyCredentials = UserCreator.withEmptyCredentials();

            logger.info("Populating email fields...");
            page.createEmail(user.getSendToEmail(), userEmptyCredentials.getEmailSubject(), user.getEmailText());

            String actualMessageWarning = page.sendALetterWithoutTheme();
            String expectedThemeWarning = page.getExpectedThemeWarning();
            assertEquals(expectedThemeWarning, actualMessageWarning);

            logger.info("Test completed successfully: Warning messages match.");
        } catch (Exception e) {
            logger.warn("An exception occurred during the test: {}", e.getMessage());
        }
    }
}
