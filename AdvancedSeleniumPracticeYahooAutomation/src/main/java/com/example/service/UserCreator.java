package com.example.service;

import com.example.model.User;

public  class UserCreator extends AbstractCreator{
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String WRONG_USERNAME = "WRONG_USERNAME";
    public static final String WRONG_PASSWORD = "WRONG_PASSWORD";
    public static final String WRONG_EMAIL = "WRONG_EMAIL";

    public static final String EMAIL= "user.email";
    public static final String FIRST_NAME="user.first_name";
    public static final String SECOND_NAME="user.second_name";
    public static final String DATE_OF_BIRTH="user.date_of_birth";
    public static final String SEND_TO_EMAIL="user.send_to_email";
    public static final String EMAIL_SUBJECT="user.email_subject";
    public static final String EMAIL_TEXT="user.email_text";


public static User withValidUsernameAndPasswordFromEnvironment(){
    try {
        String username = TestDataReader.getTestData(USERNAME);
        String password = TestDataReader.getTestData(PASSWORD);
        if (username == null || username.isEmpty()) {
            throw new IllegalStateException("Environment variable for USERNAME is not set or empty.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalStateException("Environment variable for PASSWORD is not set or empty.");
        }
        return new User.Builder()
                .withUserName(username)
                .withPassword(password)
                .build();
    } catch (IllegalStateException e) {
        throw new RuntimeException("Failed to create user due to missing or invalid environment variables: " + e.getMessage(), e);
    } catch (Exception e) {
        throw new RuntimeException("Failed to create user from properties: " + e.getMessage(), e);
    }
}
    public static User withValidEmailAndPasswordFromEnvironment(){
        try {
            String email = TestDataReader.getTestData(EMAIL);
            String password = TestDataReader.getTestData(PASSWORD);
            if (email == null || email.isEmpty()) {
                throw new IllegalStateException("Property for EMAIL is not set or empty.");
            }
            if (password == null || password.isEmpty()) {
                throw new IllegalStateException("Environment variable for PASSWORD is not set or empty.");
            }

            return new User.Builder()
                    .withEmail(email)
                    .withPassword(password)
                    .build();
        } catch (IllegalStateException e) {
            throw new RuntimeException("Failed to create user due to missing or invalid environment variables: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user from properties: " + e.getMessage(), e);
        }
    }


    public static User withCredentialFromProperty(){
        try {
            String email = validateProperty(TestDataReader.getTestData(EMAIL), "EMAIL");
            String firstName = validateProperty(TestDataReader.getTestData(FIRST_NAME), "FIRST_NAME");
            String secondName = validateProperty(TestDataReader.getTestData(SECOND_NAME), "SECOND_NAME");
            String dateOfBirth = validateProperty(TestDataReader.getTestData(DATE_OF_BIRTH), "DATE_OF_BIRTH");

            return new User.Builder()
                    .withEmail(email)
                    .withFirstName(firstName)
                    .withSecondName(secondName)
                    .withDateOfBirth(dateOfBirth)
                    .build();
        } catch (RuntimeException e) {
            throw new RuntimeException("Failed to create User from properties: " + e.getMessage(), e);
        }
    }

    public static User withEmptyCredentials(){
        return new User.Builder()
                .withUserName("")
                .withPassword("")
                .build();
    }
    public static User withWrongCredentials(){
        return new User.Builder()
                .withUserName(WRONG_USERNAME)
                .withPassword(WRONG_PASSWORD)
                .withEmail(WRONG_EMAIL)
                .build();
    }
    public static User withEmailAttributes(){
        return new User.Builder()
                .withSendToEmail(TestDataReader.getTestData(SEND_TO_EMAIL))
                .withEmailSubject(TestDataReader.getTestData(EMAIL_SUBJECT))
                .withEmailText(TestDataReader.getTestData(EMAIL_TEXT))
                .build();
    }
}
