package com.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverListener;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
/**
 * This class provides a custom implementation for actions performed before and after
 * finding an element on the web page. Specifically, it highlights the element with a
 * dashed red border after it is found and removes any previous highlighting.
 */
public class HighlighterEventListener implements WebDriverListener {
        private  WebDriver driver;
        private WebElement lastElement;

        public HighlighterEventListener(WebDriver driver) {
            this.driver = driver;
        }


        /**
         * This method is called before an element is located on the web page.
         * It removes the border styling from the previously highlighted element,
         * if any, by executing JavaScript to reset the element's border style.
         *
         * @param driver the WebDriver instance used to interact with the browser.
         * @param locator the locator (By) used to identify the element to find.
         */
        @Override
        public void beforeFindElement(WebDriver driver, By locator) {
            WebElement element = driver.findElement(locator);
            try {
                if(lastElement != null){
                    applyBorderStyle(element, "none");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            lastElement = null;
        }

        /**
         * This method is called after an element has been located on the web page.
         * It highlights the newly found element by adding a dashed red border
         * to make it visually distinct on the page.
         *
         * @param driver the WebDriver instance used to interact with the browser.
         * @param locator the locator (By) used to identify the element.
         * @param element the WebElement that was found using the given locator.
         */
            @Override
            public void afterFindElement(WebDriver driver, By locator, WebElement element) {
                lastElement = element;
                if(lastElement != null){
                    applyBorderStyle(element, "8px dashed red");
                }
            }

        /**
         * Highlights a WebElement by applying a temporary red border.
         *
         * @param element The WebElement to highlight
         */
        private void applyBorderStyle(WebElement element, String style) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].style.border='" + style + "'", element);
            WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(1));
            if(style.equals("8px dashed red")) {
                try {
                    wait.until(driver -> element.getCssValue("border").contains("8px dashed red"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            js.executeScript("arguments[0].style.border=''", element);
        }
    }

