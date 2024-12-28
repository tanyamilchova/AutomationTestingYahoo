package com.example.service;

public class AbstractCreator {

    protected static String validateProperty(String propertyValue, String propertyName) {
        if (propertyValue == null || propertyValue.isEmpty()) {
            throw new IllegalStateException("The property " + propertyName + " is missing or empty.");
        }
        return propertyValue;
    }
}
