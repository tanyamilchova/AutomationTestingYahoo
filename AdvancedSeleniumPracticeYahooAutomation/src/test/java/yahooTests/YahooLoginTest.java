package yahooTests;

import com.example.HomePage;

import com.example.model.User;
import com.example.service.UserCreator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class for verifying the login process on the Yahoo homepage.
 * The test automates the process of signing in, filling in the login name and password,
 * and asserting that the values entered match the expected values.
 */
public class YahooLoginTest extends AbstractPageTest{

    HomePage page;

    /**
     * Sets up the test environment before each test method is run.
     * This method navigates to the Yahoo homepage and initializes the HomePage object.
     */
    @BeforeMethod
    public void setup(){
        super.setup();
        driver.get("https://www.yahoo.com/");
        page = new HomePage(driver);
    }

    /**
     * Test method for the Yahoo sign-in process.
     * This method fills in the login name and password, validates the entered values,
     * and navigates through the sign-in forms.
     */
    @Test
    public void signIn(){
        User user = UserCreator.withValidPasswordAndUsernameFromProperty();
//        page.signIn();
//        page.fillLoginName(NAME);
//        String actual = page.getLoginNameValue();
//        assertEquals(NAME, actual);
//
//        page.nameFormClickNext();
//
//        page.fillPassword(PASSWORD);
//        String actualPassword = page.getPasswordValue();
//        assertEquals(PASSWORD, actualPassword);
//        page.passwordFormClickNext();
        page.signIn();
        page.fillLoginName(user.getUserName());
        String actual = page.getLoginNameValue();
        assertEquals(user.getUserName(), actual);

        page.nameFormClickNext();

        page.fillPassword(user.getPassword());
        String actualPassword = page.getPasswordValue();
        assertEquals(user.getPassword(), actualPassword);
        page.passwordFormClickNext();
    }
}
