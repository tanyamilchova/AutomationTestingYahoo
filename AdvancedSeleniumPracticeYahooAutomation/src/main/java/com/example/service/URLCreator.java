package com.example.service;

import com.example.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.example.util.Util.validateProperty;


public class URLCreator {

    private static final Logger logger = LoggerFactory.getLogger(URLCreator.class);
    private static final String LOGIN_PAGE_URL = "url.login_page";
    private static final String ACCOUNT_PAGE_URL = "url.account_page";
    private static final String MAIL_PAGE_URL = "url.mail_page";
    private static final String YAHOO_URL = "url.yahoo_page";

    public static String getLoginURLFromProperty() {
        return getURL(LOGIN_PAGE_URL, "LOGIN_PAGE_URL");
    }

    public static String getAccountURLFromProperty() {
        return getURL(ACCOUNT_PAGE_URL, "ACCOUNT_PAGE_URL");
    }

    public static String getMailURLFromProperty() {
        return getURL(MAIL_PAGE_URL, "MAIL_PAGE_URL");
    }

    public static String getYahooURLFromProperty() {
        return getURL(YAHOO_URL, "YAHOO_URL");
    }

    private static String getURL(String propertyKey, String propertyName) {
        try {
            logger.info("Attempting to retrieve URL for key: {}", propertyKey);
            String url = TestDataReader.getTestData(propertyKey);
            validateProperty(url);
            return url;
        } catch (Exception exception) {
            logger.error("Failed to get {} from properties (key: {}): {}", propertyName, propertyKey, exception.getMessage());
            throw new NotFoundException("Failed to get " + propertyName + " from properties: " + exception.getMessage());
        }
    }
}
