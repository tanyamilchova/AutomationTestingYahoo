package yahooTests;

import com.example.AbstractPage;
import com.example.HomePage;
import com.example.driver.WebDriverSingleton;
import com.example.model.User;
import com.example.service.UserCreator;
import com.example.util.TestListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
/**
 * yahooTests.AbstractPageTest serves as a base test class providing common setup and teardown
 * functionalities for web page tests. It initializes WebDriver and loads user
 * configuration properties required for the tests.
 *
 * @author [Tanya Milchova]
 * @since 1.0
 */
@Listeners({TestListener.class})
public class AbstractPageTest {
    WebDriver driver;
    User user;
    AbstractPage page;
    private  final Logger logger = LogManager.getLogger(AbstractPageTest.class);

    /**
     * Sets up the WebDriver instance before each test method.
     * Ensures that the ChromeDriver is available and initializes the WebDriver
     * using the singleton pattern.
     */
    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = WebDriverSingleton.getDriver();
    }

    /**
     * Closes the WebDriver instance after each test method.
     * Ensures that the browser is properly closed and resources are released.
     */
    @AfterMethod
    public void closeDriver(){
        WebDriverSingleton.closeDriver();
    }
}
