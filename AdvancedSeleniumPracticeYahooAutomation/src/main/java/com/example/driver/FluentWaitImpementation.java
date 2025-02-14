package com.example.driver;

import com.example.service.TestDataReader;
import com.example.util.Util;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class FluentWaitImpementation {
    protected WebDriver driver;
    protected FluentWait<WebDriver> wait;
    private final int pollingTime = Util.getTime(TestDataReader.getTestData("polling.time"));

    public FluentWaitImpementation(WebDriver driver, Duration duration) {
        this.driver = driver;
        this.wait = new FluentWait<>(driver)
                .withTimeout(duration)
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(NoSuchElementException.class);
    }
    public WebElement waitForElement(WebElement element) {
        return wait.until(driver -> {
            if (element.isDisplayed() && element.isEnabled()) {
                return element;
            } else if (element.isDisplayed()) {
                return element;
            }
            throw new NoSuchElementException("Element is neither visible nor clickable.");
        });
    }
}
