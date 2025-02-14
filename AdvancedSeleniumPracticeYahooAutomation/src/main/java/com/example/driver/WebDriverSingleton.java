package com.example.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.DriverManager;

public class WebDriverSingleton {

    private static WebDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private WebDriverSingleton(){
    }

    public static synchronized WebDriver getDriver() {
        if(driver == null){
        String browser = System.getProperty("browser", "chrome").toLowerCase();
        logger.info("Setting up WebDriver for browser: {}", browser);
        switch (browser) {
            case "chrome" ->{
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "firefox" ->{
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case "edge" ->{
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
            driver.manage().window().maximize();
        }
        return driver;
    }

    public static void closeDriver(){
        if(driver != null){
            logger.info("Closing WebDriver.");
            driver.quit();
            driver = null;
        }
    }
}
