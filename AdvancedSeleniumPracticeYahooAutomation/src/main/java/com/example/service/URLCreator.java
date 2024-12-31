package com.example.service;

public class URLCreator extends AbstractCreator{

    private static final String LOGIN_PAGE_URL = "url.login_page";
    private static final String ACCOUNT_PAGE_URL = "url.account_page";
    private static final String MAIL_PAGE_URL = "url.mail_page";
    private static final String YAHOO_URL = "url.yahoo_page";;

    public static String getLoginURLFromProperty(){
        try{
            validateProperty(TestDataReader.getTestData(LOGIN_PAGE_URL), "LOGIN_PAGE_URL");
           return TestDataReader.getTestData(LOGIN_PAGE_URL);
        }   catch (RuntimeException e) {
            throw new RuntimeException("Failed to get LOGIN_PAGE_URL from properties: " + e.getMessage(), e);
        }

    }

    public static String getAccountURLFromProperty(){
        try {
            validateProperty(TestDataReader.getTestData(ACCOUNT_PAGE_URL), "ACCOUNT_PAGE_URL");
            return  TestDataReader.getTestData(ACCOUNT_PAGE_URL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ACCOUNT_PAGE_URL from properties: " + e.getMessage(), e);
        }
    }

    public static String getMailURLFromProperty(){
        try {
            validateProperty(TestDataReader.getTestData(MAIL_PAGE_URL), "MAIL_PAGE_URL");
            return  TestDataReader.getTestData(MAIL_PAGE_URL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get MAIL_PAGE_URL from properties: " + e.getMessage(), e);
        }
    }

    public static String getYahooURLFromProperty(){
        try {
            String mailPageURL = validateProperty(TestDataReader.getTestData(YAHOO_URL), "MAIL_PAGE_URL");
            return  TestDataReader.getTestData(mailPageURL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get MAIL_PAGE_URL from properties: " + e.getMessage(), e);
        }
    }
}
