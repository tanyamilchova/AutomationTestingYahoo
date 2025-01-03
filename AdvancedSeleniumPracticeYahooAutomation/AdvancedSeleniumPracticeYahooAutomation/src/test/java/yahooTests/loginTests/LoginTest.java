package yahooTests.loginTests;

import com.example.page.HomePage;
import com.example.page.PageFactory;
import com.example.model.User;
import com.example.service.TestDataReader;
import com.example.service.URLCreator;
import com.example.service.UserCreator;
import com.example.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import yahooTests.AbstractPageTest;

import static org.testng.Assert.assertThrows;
import static org.testng.AssertJUnit.*;

/**
 * Test class for verifying the login process on the Yahoo homepage.
 * The test automates the process of signing in, filling in the login name and password,
 * and asserting that the values entered match the expected values.
 */
public class LoginTest extends AbstractPageTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);
    HomePage page;
    User user;
    String  HOME_PAGE = "page.home";
    /**
     * Sets up the test environment before each test method is run.
     * This method navigates to the Yahoo homepage and initializes the HomePage object.
     */
    @BeforeMethod
    public void setup(){
        try {
            super.setup();
            logger.info("Navigating to Yahoo homepage.");
            driver.get(URLCreator.getLoginURLFromProperty());

            String homePage = Util.validateProperty(TestDataReader.getTestData(HOME_PAGE));
            page = (HomePage) PageFactory.getPage(driver, homePage);

            logger.debug("HomePage object initialized.");
        }
        catch (Exception e) {
        logger.error("Error occurred during setup: {}", e.getMessage(),e);
        throw e;
        }
    }

    /**
     * Test method for the Yahoo sign-in process.
     * This method fills in the login name and password, validates the entered values,
     * and navigates through the sign-in forms.
     */
    @Test
    public void signIn(){
        logger.info("Starting sign-in test.");
        user = UserCreator.withValidUsernameAndPasswordFromEnvironment();
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


    @DataProvider
    public Object[][] loginWithWrongEmailAndPassword(){
        user = UserCreator.withEmptyCredentials();
        User userWrongCredentials = UserCreator.withWrongCredentials();
        return new Object[][] {
                {userWrongCredentials.getEmail(), user.getPassword()},
                {user.getEmail(), userWrongCredentials.getPassword()},
                {userWrongCredentials.getEmail(), userWrongCredentials
                        .getPassword()}
        };
    }

    @Test(dataProvider = "loginWithWrongEmailAndPassword")
    public void loginWrongCredentials(String email, String password) {
        User wrongCredentialsUser = UserCreator.withWrongCredentials();
        logger.info("Testing login with email: {} and password: {}", email, password);

        assertThrows("Login should fail for invalid credentials!", Exception.class, () -> {
            page.signIn();
            page.fillLoginName(wrongCredentialsUser.getEmail());
            page.nameFormClickNext();

            String errorMessage = page.getWrongEmailMessage();
            assertTrue(errorMessage.contains("This account has been deactivated due to inactivity," +
                    " but we would love to welcome you back! Click Sign up below to create your new account"));
            logger.debug("Verified error message for invalid username: {}", errorMessage);
        });
    }
}
