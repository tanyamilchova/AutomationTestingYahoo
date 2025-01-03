package com.example.page;

import org.openqa.selenium.WebDriver;

public class PageFactory {

    public static AbstractPage getPage(WebDriver driver, String pageName){
        return switch (pageName.toLowerCase()) {
            case "home" -> new HomePage(driver);
            case "mail" -> new MailPage(driver);
            case "mail_copy_to_decorator" -> new MailPageDecorator(new MailPage(driver));
            case "personal_info" -> new PersonalInfoPage(driver);
            default -> throw new IllegalArgumentException("Unknown page: " + pageName);
        };
    }
}
