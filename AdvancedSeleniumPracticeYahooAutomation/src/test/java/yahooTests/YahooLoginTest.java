package yahooTests;

import com.example.HomePage;

import com.example.model.User;
import com.example.service.URLCreator;
import com.example.service.UserCreator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class for verifying the login process on the Yahoo homepage.
 * The test automates the process of signing in, filling in the login name and password,
 * and asserting that the values entered match the expected values.
 */
public class YahooLoginTest extends AbstractPageTest{

    private static final Logger logger = LogManager.getLogger(YahooLoginTest.class);
    HomePage page;
    User user;
    /**
     * Sets up the test environment before each test method is run.
     * This method navigates to the Yahoo homepage and initializes the HomePage object.
     */
    @BeforeMethod
    public void setup(){
        super.setup();
        logger.info("Navigating to Yahoo homepage.");
        driver.get(URLCreator.getLoginURLFromProperty());

        page = new HomePage(driver);
        logger.debug("HomePage object initialized.");
    }

    /**
     * Test method for the Yahoo sign-in process.
     * This method fills in the login name and password, validates the entered values,
     * and navigates through the sign-in forms.
     */
    @Test
    public void signIn(){
        logger.info("Starting sign-in test.");

        User user = UserCreator.withValidUsernameAndPasswordFromEnvironment();
//        user = UserCreator.withValidEmailAndPasswordFromEnvironment();
        logger.debug("User credentials retrieved: Username = {}, Password = [PROTECTED]", user.getUserName());

        try {
            logger.info("Initiating sign-in process.");
            page.signIn();

            logger.info("Filling login name.");
            page.fillLoginName(user.getUserName());
            String actual = page.getLoginNameValue();
            assertEquals(user.getUserName(), actual);
            logger.debug("Login name entered correctly: {}", actual);

            page.nameFormClickNext();
            logger.info("Proceeding to the next step after entering login name.");

            logger.info("Filling password.");
            page.fillPassword(user.getPassword());
            String actualPassword = page.getPasswordValue();
            assertEquals(user.getPassword(), actualPassword);
            logger.debug("Password entered correctly: {}", actualPassword);

            page.passwordFormClickNext();
            logger.info("Proceeding after password entry.");
        } catch (Exception e) {
            logger.error("Error during the sign-in process.", e);
            throw e;
        }

        logger.info("Sign-in test completed successfully.");
    }
}
