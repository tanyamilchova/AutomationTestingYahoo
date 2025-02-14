package com.example.page;

import org.openqa.selenium.WebDriver;

import java.util.Map;
import java.util.function.Function;

public class PageFactory {

    private static final Map<String, Function<WebDriver, AbstractPage>> PAGE_MAP = Map.of(
            "home", HomePage::new,
            "mail", MailPage::new,
            "mail_copy_to_decorator", driver -> new MailPageDecorator(new MailPage(driver)),
            "personal_info", PersonalInfoPage::new
    );

    public static AbstractPage getPage(WebDriver driver, String pageName) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null.");
        }
        if (pageName == null || pageName.trim().isEmpty()) {
            throw new IllegalArgumentException("Page name cannot be null or empty.");
        }

        Function<WebDriver, AbstractPage> pageConstructor = PAGE_MAP.get(pageName.toLowerCase());

        if (pageConstructor == null) {
            throw new IllegalArgumentException("Unknown page: " + pageName);
        }
        return pageConstructor.apply(driver);
    }
}