package com.example.service;

import com.example.model.User;

public  class UserCreator {
    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String EMAIL= "EMAIL";

    public static final String FIRST_NAME="user.first_name";
    public static final String SECOND_NAME="user.second_name";
    public static final String DATE_OF_BIRTH="user.date_of_birth";
    public static final String SEND_TO_EMAIL="user.send_to_email";
    public static final String EMAIL_SUBJECT="user.email_subject";
    public static final String EMAIL_TEXT="user.email_text";


public static User withValidPasswordAndUsernameFromProperty(){
        return new User.Builder()
                .withUserName(TestDataReader.getTestData(USERNAME))
                .withPassword(TestDataReader.getTestData(PASSWORD))
                .build();
}
    public static User withValidPasswordAndEmailFromProperty(){
        return new User.Builder()
                .withEmail(TestDataReader.getTestData(EMAIL))
                .withPassword(TestDataReader.getTestData(PASSWORD))
                .build();
    }
    public static User withCredentialFromProperty(){
        return new User.Builder()
        .withUserName(TestDataReader.getTestData(USERNAME))
                .withPassword(TestDataReader.getTestData(PASSWORD))
                .withEmail(TestDataReader.getTestData(EMAIL))
                .withFirstName(TestDataReader.getTestData(FIRST_NAME))
                .withSecondName(TestDataReader.getTestData(SECOND_NAME))
                .withDateOfBirth(TestDataReader.getTestData(DATE_OF_BIRTH))
                .build();

    }
    public static User withEmptyCredential(){
        return new User.Builder()
                .withUserName("")
                .withPassword("")
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
