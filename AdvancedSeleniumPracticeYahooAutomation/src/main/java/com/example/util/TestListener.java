package com.example.util;

import com.example.driver.WebDriverSingleton;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    private static final Logger logger = LoggerFactory.getLogger(TestListener.class);
    private static final String SCREENSHOT_DIR = System.getProperty("screenshot.path", Paths.get(System.getProperty("user.dir"), "screenshots").toString());

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = WebDriverSingleton.getDriver();
        if (driver instanceof TakesScreenshot) {
            takeScreenshot((TakesScreenshot) driver, result);
        } else {
            logger.warn("Driver does not support taking screenshots.");
        }
    }

    private void takeScreenshot(TakesScreenshot driver, ITestResult result) {
        File screenshot = driver.getScreenshotAs(OutputType.FILE);
        File screenshotFolder = new File(SCREENSHOT_DIR);
        if (!screenshotFolder.exists() && !screenshotFolder.mkdirs()) {
            logger.error("Failed to create screenshot directory: {}", SCREENSHOT_DIR);
            return;
        }

        String screenshotName = String.format("%s_%d.png", result.getName(), System.currentTimeMillis());
        File destinationFile = new File(screenshotFolder, screenshotName);

        try {
            FileUtils.copyFile(screenshot, destinationFile);
            logger.info("Screenshot saved: {}", destinationFile.getAbsolutePath());
        } catch (IOException e) {
            logger.error("Failed to save screenshot: {}", destinationFile.getAbsolutePath(), e);
        }
    }
}
