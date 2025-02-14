package com.example.service;

import com.example.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;


public class TestDataReader {
    private static final Logger logger = LoggerFactory.getLogger(TestDataReader.class);

   public static ResourceBundle resourceBundle ;

   private static final String ENVIRONMENT = System.getenv("ENVIRONMENT") != null ?
           System.getenv("ENVIRONMENT") :
           System.getProperty("environment", "qa");

   private static ResourceBundle getResourceBundle() {
       if (resourceBundle == null) {
           resourceBundle = ResourceBundle.getBundle(ENVIRONMENT);
       }
       return resourceBundle;
   }
   public static String getTestData(String key) {
       if (key == null || key.isBlank()) {
           logger.warn("Attempted to retrieve a blank or null property key.");
           throw new IllegalArgumentException("The property is null or empty.");
       }

       String envValue = System.getenv(key);
       if (envValue != null && !envValue.trim().isBlank()) {
           return envValue;
       }
       try {
           return getResourceBundle().getString(key);
       } catch (Exception exception) {
          logger.error("Failed to retrieve property key: {}", key, exception);
           throw new NotFoundException("Key: " + key + " does not exist in properties");
       }
   }
}
