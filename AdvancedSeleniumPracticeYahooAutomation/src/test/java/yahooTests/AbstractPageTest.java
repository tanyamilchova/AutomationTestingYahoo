package yahooTests;

import com.example.util.HighlighterEventListener;
import com.example.driver.WebDriverSingleton;
import com.example.util.TestListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
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
   public WebDriver driver;


    /**
     * Sets up the WebDriver instance before each test method.
     * Ensures that the ChromeDriver is available and initializes the WebDriver
     * using the singleton pattern.
     */
    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        WebDriver baseDriver = WebDriverSingleton.getDriver();

        WebDriverListener highlightingListener = new HighlighterEventListener(baseDriver);
        driver = new EventFiringDecorator(highlightingListener).decorate(baseDriver);

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
