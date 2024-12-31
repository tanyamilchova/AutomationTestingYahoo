package com.example.util;

import com.example.driver.WebDriverSingleton;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;

public class TestListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = WebDriverSingleton.getDriver();
        if (driver != null) {
            takeScreenshot(driver);
        } else {
            System.err.println("Driver is null. Cannot take screenshot.");
        }
    }

    private void takeScreenshot(WebDriver driver) {
        File screenshot  = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        File screenshotFolder = new File(System.getProperty("user.dir"), "screenshots");
        screenshotFolder.mkdirs();
        try {
            FileUtils.copyFile(screenshot, new File(screenshotFolder, System.currentTimeMillis() + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
