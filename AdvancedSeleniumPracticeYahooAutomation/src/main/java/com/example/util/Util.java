package com.example.util;

import com.example.exceptions.InvalidConfigurationException;
import com.example.exceptions.PropertyException;
import com.example.service.TestDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
    private static final Logger logger = LoggerFactory.getLogger(Util.class);


    public static String validateProperty(String propertyValue) {
        if (propertyValue == null || propertyValue.trim().isBlank()) {
            logger.error("Invalid property: '{}' has a null, empty or blank value.", propertyValue);
            throw new InvalidConfigurationException("Property '" + propertyValue + "' is missing or empty.");
        }
        return propertyValue.trim();
    }


    public static int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.error("Invalid number format for value '{}'.", value, e);
            throw new PropertyException("Invalid number format for value: " + value);
        }
    }
    public static int getTime(String time) {
        try {
            return Util.parseIntSafe(time);
        } catch (PropertyException exception) {
            logger.warn("Error parsing time. Using default time: " + TestDataReader.getTestData("wait.default.time"));
            return parseIntSafe(TestDataReader.getTestData("wait.default.time"));
        }
    }
}
