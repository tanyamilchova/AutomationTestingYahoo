package com.example.service;

public class URLCreator {

    private static final String LOGIN_PAGE_URL = "url.login_page";
    private static final String ACCOUNT_PAGE_URL = "url.account_page";
    private static final String MAIL_PAGE_URL = "url.mail_page";

    public static String getLoginURLFromProperty(){
        return  TestDataReader.getTestData(LOGIN_PAGE_URL);
    }
    public static String getAccountURLFromProperty(){
        return  TestDataReader.getTestData(ACCOUNT_PAGE_URL);
    }
    public static String getMailURLFromProperty(){
        return  TestDataReader.getTestData(MAIL_PAGE_URL);
    }
}
