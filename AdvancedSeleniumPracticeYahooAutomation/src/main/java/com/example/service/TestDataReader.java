package com.example.service;

import java.util.ResourceBundle;

public class TestDataReader {
    static {
        System.setProperty("environment", "qa");
    }
    public static ResourceBundle resourceBundle=ResourceBundle.getBundle(System.getProperty("environment"));
    public static String getTestData(String key){
        if(key != null){
            System.out.println("Key: ..............."+key);
            String envValue = System.getenv(key);
            if (envValue != null && !envValue.trim().isEmpty()) {
                return envValue;
            }
        }
        try {
            return resourceBundle.getString(key);
        } catch (Exception e) {
            return null;
        }
    }
}
