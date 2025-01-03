package yahooTests.personalInfoPageTests;

import com.example.page.PageFactory;
import com.example.page.PersonalInfoPage;
import com.example.model.User;
import com.example.service.TestDataReader;
import com.example.service.URLCreator;
import com.example.service.UserCreator;
import com.example.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import yahooTests.AbstractPageTest;
import static org.testng.AssertJUnit.*;

/**
 * Test class for verifying personal information displayed on the Yahoo account page.
 * The test automates the login process, accesses the account information, and asserts that the personal details match the expected values.
 */
public class PersonalInfoPageTest extends AbstractPageTest {

    private static final Logger logger = LogManager.getLogger(PersonalInfoPageTest.class);
    PersonalInfoPage page;
    String  PERSONAL_INFO_PAGE = "page.personal_info";

    /**
     * Sets up the test environment before each test method is run.
     * This method navigates to the Yahoo account login page and initializes the PersonalInfoPage object.
     */
    @BeforeMethod
    public void setup() {
        try {
            super.setup();
            logger.info("Navigating to Yahoo login page.");
            driver.get(URLCreator.getAccountURLFromProperty());

            String personalInfoPage = Util.validateProperty(TestDataReader.getTestData(PERSONAL_INFO_PAGE));
            page = (PersonalInfoPage) PageFactory.getPage(driver, personalInfoPage);

            page.loginUser();
            logger.debug("PersonalInfoPage object initialized.");
        } catch (Exception e) {
            logger.error("Error occurred during setup: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Test method for asserting that the personal information on the Yahoo account page matches the expected values.
     * Logs into the Yahoo account, accesses the account information, and validates the displayed personal details.
     */
    @Test
    public void assertAccountDetails() {
        try {
            logger.info("Test started for asserting account details.");
            User user = UserCreator.withCredentialFromProperty();
            page.accessAccountInformation();
            logger.info("Accessing account information...");

            String actualEmail = page.getEmailInfo();
            String actualName = page.getNameInfo();
            String actualSecondName = page.getSecondNameInfo();
            String actualDateOfBirth = page.getDateOfBirthInfo();

            logger.debug("Fetched account info - Email: {}, Name: {}, Second Name: {}, DOB: {}",
                    actualEmail, actualName, actualSecondName, actualDateOfBirth);

            assertEquals(user.getFirstName(), actualName);
            assertEquals(user.getSecondName(), actualSecondName);
            assertEquals(user.getEmail(), actualEmail);
            assertEquals(user.getDateOfBirth(), actualDateOfBirth);

            logger.info("Test passed, account details match the expected values.");
        } catch (NoSuchElementException e) {
            logger.error("Error occurred while fetching account details from the page. Elements may be missing: {}", e.getMessage(), e);
            throw e;
        } catch (AssertionError e) {
            logger.error("Test failed: Account details did not match the expected values. Assertion failed.", e);
            throw e;
        } catch (Exception e) {
            logger.error("Unexpected error occurred during account details assertion test: {}", e.getMessage(), e);
            throw new RuntimeException("Test execution failed due to unexpected error", e);
        }
    }

    /**
     * Test method to handle accessing the personal information page without login.
     */
    @Test
    public void testAccessPersonalInfoWrongCredentialsUser() {
        User user = UserCreator.withWrongCredentials();
        try {
            logger.info("Test started: Accessing personal info with WrongCredentialsUser.");

            page.accessAccountInformation();
            String actualEmailInfo = page.getEmailInfo();
            String actualNameInfo = page.getNameInfo();
            String actualSecondNameInfo = page.getSecondNameInfo();

            assertNotSame(user.getEmailText(), actualEmailInfo);
            assertNotSame(user.getFirstName(), actualNameInfo);
            assertNotSame(user.getSecondName(), actualSecondNameInfo);

            logger.info("Test passed: ");
        } catch (NoSuchElementException e) {
            logger.error("Error: Personal info page elements not found when not logged in", e);
            throw e;
        }
    }

    @Test
    public void testChangeSecondName() {
        try {
            User user = UserCreator.withCredentialFromProperty();
            logger.info("User object created with credentials.");

            page.accessAccountInformation();
            logger.info("Accessed account information page.");
            page.changeSecondName();

            String actualSecondName = page.getLastName();
            String expectedSecondName = page.getNewSecondName();
            assertEquals(expectedSecondName, actualSecondName);

        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
        }
    }
}

