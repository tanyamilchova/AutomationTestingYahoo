package com.example.util;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * This class provides a custom implementation for actions performed before and after
 * finding an element on the web page. Specifically, it highlights the element with a
 * dashed red border after it is found and removes any previous highlighting.
 */
public class HighlighterEventListener implements WebDriverListener {
    private WebDriver driver;

    public HighlighterEventListener(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * This method is called after an element has been located on the web page.
     * It highlights the newly found element by adding a dashed red border
     * to make it visually distinct on the page.
     *
     * @param driver  the WebDriver instance used to interact with the browser.
     * @param locator the locator (By) used to identify the element.
     * @param element the WebElement that was found using the given locator.
     */
    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement element) {
        if (element != null) {
            applyBorderStyle(element, "8px dashed red");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until((WebDriver d) -> element.isDisplayed());

            removeBorderStyle(element);
        }
    }

    /**
     * Highlights a WebElement by applying a red border.
     *
     * @param element The WebElement to highlight
     * @param style   The border style to apply (e.g., "8px dashed red")
     */
    private void applyBorderStyle(WebElement element, String style) {
        if (element != null) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='" + style + "'", element);
        }
    }

    /**
     * Removes the border style from a WebElement.
     *
     * @param element The WebElement to reset
     */
    private void removeBorderStyle(WebElement element) {
        if (element != null) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].style.border=''", element);
            } catch (StaleElementReferenceException e) {
                System.out.println("Element is no longer attached to the DOM: " + e.getMessage());
            }
        }
    }
}

