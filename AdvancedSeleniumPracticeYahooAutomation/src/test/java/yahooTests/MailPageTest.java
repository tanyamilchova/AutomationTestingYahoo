package yahooTests;

import com.example.MailPage;

import com.example.model.User;
import com.example.service.TestDataReader;
import com.example.service.URLCreator;
import com.example.service.UserCreator;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
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
    User user;
    private  final Logger logger = LogManager.getLogger(MailPageTest.class);

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
            page = new MailPage(driver);

            user = UserCreator.withValidEmailAndPasswordFromEnvironment();

            page.login(user.getEmail(), user.getPassword());
            logger.info("Logged in with email: " + user.getEmail());
        } catch (Exception e) {
            logger.error("Error occured during setup: {}", e.getMessage(),e);
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
    public void createNewEmail(){
        logger.info("Testing creation of new email...");
        User user = UserCreator.withEmailAttributes();
        int sentLetters = page.getNumberOfSentLetters();
        page.createEmail(user.getSendToEmail(), user.getEmailSubject(), user.getEmailText());
        logger.debug("Email created with recipient: " + user.getSendToEmail() + ", subject: " + user.getEmailSubject());

        page.refreshLetterBox();
        int currentSentLetters = page.getNumberOfSentLetters();
        assertEquals((sentLetters + 1), currentSentLetters);
        logger.info("New email sent successfully. Sent letters count: " + currentSentLetters);
    }

    /**
     * Tests the creation of a new draft.
     * <p>
     * This method verifies that a draft can be created and that the number
     * of drafts increases by one after creation.
     * </p>
     */
    @Test
    public void createNewDraft(){
        logger.info("Testing creation of new draft...");
        User user = UserCreator.withEmailAttributes();
        int numberDrafts = page.getNumberOfDrafts();
        page.createDraft(user.getSendToEmail(), user.getEmailSubject(), user.getEmailText());
        logger.debug("Draft created with recipient: " + user.getSendToEmail() + ", subject: " + user.getEmailSubject());

        page.refreshDraftCheckboxBox();
        int currentNumberOfDrafts = page.getNumberOfDrafts();
        assertEquals((numberDrafts + 1), currentNumberOfDrafts);
        logger.info("New draft created successfully. Draft count: " + currentNumberOfDrafts);
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
    public void sendADraft(){
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
        page.refreshDraftBox();

        int currentNumberOfDrafts = page.getNumberOfDrafts();
        assertEquals((numberDrafts - 1), currentNumberOfDrafts);
        logger.info("Draft email sent successfully. Remaining drafts: " + currentNumberOfDrafts);
    }

    /**
     * Tests logging out from the mail account.
     * <p>
     * This method verifies that after logging out, the current URL matches
     * the expected URL, indicating a successful logout.
     * </p>
     */
    @Test
    public void logOut(){
        logger.info("Testing logout...");
        final String EXPECTED_URL = "https://www.yahoo.com/";
        page.exitAccount();

        String actual_URL = driver.getCurrentUrl();
        assertEquals(EXPECTED_URL, actual_URL);
        logger.info("Logout successful. Current URL: " + actual_URL);
    }
}
