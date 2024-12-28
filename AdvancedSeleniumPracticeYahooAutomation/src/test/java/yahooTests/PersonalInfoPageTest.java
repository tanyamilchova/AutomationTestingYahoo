package yahooTests;

import com.example.PersonalInfoPage;

import com.example.model.User;
import com.example.service.URLCreator;
import com.example.service.UserCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class for verifying personal information displayed on the Yahoo account page.
 * The test automates the login process, accesses the account information, and asserts that the personal details match the expected values.
 */
public class PersonalInfoPageTest extends AbstractPageTest {

    private static final Logger logger = LogManager.getLogger(PersonalInfoPageTest.class);
    PersonalInfoPage page ;
    User user;

    /**
     * Sets up the test environment before each test method is run.
     * This method navigates to the Yahoo account login page and initializes the PersonalInfoPage object.
     */
    @BeforeMethod
    public void setup(){
        super.setup();
        logger.info("Navigating to Yahoo login page.");
        driver.get(URLCreator.getAccountURLFromProperty());
        page = new PersonalInfoPage(driver);

        user = UserCreator.withValidEmailAndPasswordFromEnvironment();
        page.login(user.getEmail(), user.getPassword());
        logger.info("Logged in with email: " + user.getEmail());

        logger.debug("PersonalInfoPage object initialized.");
    }

    /**
     * Test method for asserting that the personal information on the Yahoo account page matches the expected values.
     * Logs into the Yahoo account, accesses the account information, and validates the displayed personal details.
     */
    @Test
    public void assertAccountDetails(){
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

        try {
            assertEquals(user.getFirstName(), actualName);
            assertEquals(user.getSecondName(), actualSecondName);
            assertEquals(user.getEmail(), actualEmail);
            assertEquals(user.getDateOfBirth(), actualDateOfBirth);
            logger.info("Test passed, account details match the expected values.");
        } catch (AssertionError e) {
            logger.error("Test failed: Account details did not match the expected values.", e);
            throw e;
        }
    }
}

