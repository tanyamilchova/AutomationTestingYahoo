package com.example.util;

public class Util {

    public static String validateProperty(String propertyValue) {
        if (propertyValue == null || propertyValue.isEmpty()) {
            throw new IllegalStateException("The property " + propertyValue + " is missing or empty.");
        }
        return propertyValue;
    }
}
