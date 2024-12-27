package yahooTests;

import com.example.PersonalInfoPage;

import com.example.model.User;
import com.example.service.UserCreator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Test class for verifying personal information displayed on the Yahoo account page.
 * The test automates the login process, accesses the account information, and asserts that the personal details match the expected values.
 */
public class PersonalInfoPageTest extends AbstractPageTest {

    PersonalInfoPage page ;

    /**
     * Sets up the test environment before each test method is run.
     * This method navigates to the Yahoo account login page and initializes the PersonalInfoPage object.
     */
    @BeforeMethod
    public void setup(){
        super.setup();
        driver.get("https://login.yahoo.com/myaccount/overview/?src=ym&.done=https%3A%2F%2Fmail.yahoo.com%2F&pspid=159608517&activity=ybar-acctinfo&.scrumb=2ePeeNL%2FjRy");
        page = new PersonalInfoPage(driver);
    }

    /**
     * Test method for asserting that the personal information on the Yahoo account page matches the expected values.
     * Logs into the Yahoo account, accesses the account information, and validates the displayed personal details.
     */
    @Test
    public void assertAccountDetails(){
        User user = UserCreator.withCredentialFromProperty();

        page.login(user.getEmail(), user.getPassword());
        page.accessAccountInformation();

        String actualEmail = page.getEmailInfo();
        String actualName = page.getNameInfo();
        String actualSecondName = page.getSecondNameInfo();
        String actualDateOfBirth = page.getDateOfBirthInfo();

        assertEquals(user.getFirstName(), actualName);
        assertEquals(user.getSecondName(), actualSecondName);
        assertEquals(user.getEmail(), actualEmail);
        assertEquals(user.getDateOfBirth(), actualDateOfBirth);
    }
}

