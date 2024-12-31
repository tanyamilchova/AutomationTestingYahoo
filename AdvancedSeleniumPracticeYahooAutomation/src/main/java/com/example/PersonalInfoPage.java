package com.example;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;



public class PersonalInfoPage extends AbstractPage{
    private final Logger logger = LoggerFactory.getLogger(PersonalInfoPage.class);
    public PersonalInfoPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(how = How.CSS, using = "a._yb_whwfeb.rapid-noclick-resp[href*=\"personalinfo\"]")
    WebElement personalInfo;
    @FindBy(how = How.XPATH, using = "//span[@title='margot11margot11@yahoo.com']")
    WebElement emailInfoContainer;
    @FindBy(how = How.XPATH, using = "//span[text()='Собствено име']/following-sibling::span")
    WebElement nameInfoContainer;
    @FindBy(how = How.XPATH, using = "//span[text()='Фамилно име']/following-sibling::span[@class='idc-status-value']")
    WebElement secondNameInfoContainer;
    @FindBy(how = How.XPATH, using = "//span[text()='1 януари 1990 г.']")
    WebElement dateOfBirthInfoContainer;
    @FindBy(how = How.CSS, using = ".dob-img")
    WebElement dobImage;
    @FindBy(how = How.XPATH, using = "//a[text()='Актуализиране на личните данни']")
    WebElement changePersonalData;
    @FindBy(how = How.XPATH, using = "//input[@id='txtLastName']")
    WebElement lastNameContainer;
    @FindBy(how = How.XPATH, using = "//button[@id='btnSave']")
    WebElement savePersonalData;
    @FindBy(how = How.XPATH, using = "//span[text()='Фамилно име']/following-sibling::span")
    WebElement correctedSecondName;
    public static final String NEW_SECOND_NAME = "user.new_second_name";

    public void accessAccountInformation(){
        personalInfo.click();
    }

    public String getEmailInfo() {
        try {
            waitForElementToBeVisible(emailInfoContainer);
            return emailInfoContainer.getDomAttribute("title");
        } catch (NoSuchElementException e) {
            logger.error("Email info element not found: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving email info: {}", e.getMessage());
            return "";
        }
    }

    public String getNameInfo() {
        try {
            waitForElementToBeVisible(nameInfoContainer);
            return nameInfoContainer.getText();
        } catch (NoSuchElementException e) {
            logger.error("Name info element not found: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving name info: {}", e.getMessage());
            return "";
        }
    }

    public String getSecondNameInfo() {
        try {
            waitForElementToBeVisible(secondNameInfoContainer);
            return secondNameInfoContainer.getText();
        } catch (NoSuchElementException e) {
            logger.error("Second name info element not found: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving second name info: {}", e.getMessage());
            return "";
        }
    }

    /**
     * Retrieves and formats the user's Date of Birth information.
     * This method performs the following actions:
     * - Waits for the Date of Birth image element to be visible and clicks it.
     * - Extracts the displayed date from the Date of Birth info container.
     * - Parses the date from its original format (e.g., "d MMMM yyyy г.") using the Bulgarian locale.
     * - Formats the parsed date into the desired output format (e.g., "dd.MM.yyyy").
     * If any issues are encountered during the process (e.g., element not found, parsing error), the method logs the error and returns `null`.
     *
     * @return the formatted Date of Birth in the format "dd.MM.yyyy", or `null` if an error occurs.
     */

    public String getDateOfBirthInfo() {
        try {
            waitForElementToBeVisible(dobImage);
            dobImage.click();
            logger.debug("Date of Birth image clicked.");

            String displayedDate = dateOfBirthInfoContainer.getText();
            logger.debug("Displayed Date: {}", displayedDate);

            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", new Locale("bg", "BG"));
            LocalDate date = LocalDate.parse(displayedDate, inputFormatter);
            logger.debug("Parsed LocalDate: {}", date);

            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String formattedDate = date.format(outputFormatter);
            logger.info("Formatted Date of Birth: {}", formattedDate);

            return formattedDate;
        } catch (NoSuchElementException e) {
            logger.error("Date of Birth element not found: {}", e.getMessage());
            return null;
        } catch (DateTimeParseException e) {
            logger.error("Error parsing the Date of Birth: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while fetching Date of Birth info: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Changes the user's second (last) name by interacting with the relevant UI elements.
     * This method performs the following actions:
     * - Waits for the "Change Personal Data" button to be clickable and clicks it.
     * - Clears the current second name (last name) field and enters a new last name ("New LastName").
     * - Clicks the "Save Personal Data" button to save the changes.
     * If any issues are encountered during the process (e.g., elements not found, unexpected errors), the method logs the error.
     *
     * @throws NoSuchElementException if any of the expected elements are not found in the DOM.
     */
    public void changeSecondName() {
        try {
            waitForElementToBeClickable(changePersonalData);
            changePersonalData.click();

            lastNameContainer.clear();
            lastNameContainer.sendKeys("New LastName");

            savePersonalData.click();
        } catch (NoSuchElementException e) {
            logger.error("Second name info element not found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving second name info: {}", e.getMessage());
        }
    }

    public String getLastName() {
        waitForElementToBeVisible(correctedSecondName);
        return correctedSecondName.getText();
    }

    public String getNewSecondName() {
        return NEW_SECOND_NAME;
    }
}
