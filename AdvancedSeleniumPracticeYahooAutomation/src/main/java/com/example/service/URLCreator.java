package com.example.service;

public class URLCreator extends AbstractCreator{

    private static final String LOGIN_PAGE_URL = "url.login_page";
    private static final String ACCOUNT_PAGE_URL = "url.account_page";
    private static final String MAIL_PAGE_URL = "url.mail_page";

    public static String getLoginURLFromProperty(){
        try{
            String loginPageURL = validateProperty(TestDataReader.getTestData(LOGIN_PAGE_URL), "LOGIN_PAGE_URL");
           return TestDataReader.getTestData(loginPageURL);
        }   catch (RuntimeException e) {
            throw new RuntimeException("Failed to get LOGIN_PAGE_URL from properties: " + e.getMessage(), e);
        }

    }
    public static String getAccountURLFromProperty(){
        try {
            String accountPageURL = validateProperty(TestDataReader.getTestData(ACCOUNT_PAGE_URL), "ACCOUNT_PAGE_URL");
            return  TestDataReader.getTestData(accountPageURL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get ACCOUNT_PAGE_URL from properties: " + e.getMessage(), e);
        }
    }
    public static String getMailURLFromProperty(){
        try {
            String mailPageURL = validateProperty(TestDataReader.getTestData(MAIL_PAGE_URL), "MAIL_PAGE_URL");
            return  TestDataReader.getTestData(mailPageURL);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get MAIL_PAGE_URL from properties: " + e.getMessage(), e);
        }
    }
}
