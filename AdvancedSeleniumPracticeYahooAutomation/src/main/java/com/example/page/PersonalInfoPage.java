package com.example.page;

import com.example.service.TestDataReader;
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



public class PersonalInfoPage extends AbstractPage {
    private final Logger logger = LoggerFactory.getLogger(PersonalInfoPage.class);
    public PersonalInfoPage(WebDriver driver) {
        super(driver);
    }

    private static final String PERSONAL_INFO_LOCATOR = "a._yb_whwfeb.rapid-noclick-resp[href*=\"personalinfo\"]";
    private static final String EMAIL_INFO_CONTAINER_LOCATOR = "//span[@title='margot11margot11@yahoo.com']";
    private static final String NAME_INFO_CONTAINER_LOCATOR = "//span[text()='Собствено име']/following-sibling::span";
    private static final String SECOND_NAME_INFO_CONTAINER_LOCATOR = "//span[text()='Фамилно име']/following-sibling::span[@class='idc-status-value']";
    private static final String DATE_OF_BIRTH_INFO_CONTAINER_LOCATOR = "//span[text()='1 януари 1990 г.']";
    private static final String DOB_IMAGE_LOCATOR = ".dob-img";
    private static final String CHANGE_PERSONAL_DATA_LOCATOR = "//a[text()='Актуализиране на личните данни']";
    private static final String LAST_NAME_CONTAINER_LOCATOR = "txtLastName";
    private static final String SAVE_PERSONAL_DATA_LOCATOR = "btnSave";
    private static final String CORRECTED_SECOND_NAME_LOCATOR = "//span[text()='Фамилно име']/following-sibling::span";

    @FindBy(how = How.CSS, using = PERSONAL_INFO_LOCATOR)
    public WebElement personalInfo;

    @FindBy(how = How.XPATH, using = EMAIL_INFO_CONTAINER_LOCATOR)
    public WebElement emailInfoContainer;

    @FindBy(how = How.XPATH, using = NAME_INFO_CONTAINER_LOCATOR)
    public WebElement nameInfoContainer;

    @FindBy(how = How.XPATH, using = SECOND_NAME_INFO_CONTAINER_LOCATOR)
    public WebElement secondNameInfoContainer;

    @FindBy(how = How.XPATH, using = DATE_OF_BIRTH_INFO_CONTAINER_LOCATOR)
    public WebElement dateOfBirthInfoContainer;

    @FindBy(how = How.CSS, using = DOB_IMAGE_LOCATOR)
    public WebElement dobImage;

    @FindBy(how = How.XPATH, using = CHANGE_PERSONAL_DATA_LOCATOR)
    public WebElement changePersonalData;

    @FindBy(how = How.ID, using = LAST_NAME_CONTAINER_LOCATOR)
    public WebElement lastNameContainer;

    @FindBy(how = How.ID, using = SAVE_PERSONAL_DATA_LOCATOR)
    public WebElement savePersonalData;


    @FindBy(how = How.XPATH, using = CORRECTED_SECOND_NAME_LOCATOR)
    public WebElement correctedSecondName;
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
            logger.error("Changing the second name info element - not found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while changing second name info: {}", e.getMessage());
        }
    }

    public String getLastName() {
        waitForElementToBeVisible(correctedSecondName);
        return correctedSecondName.getText();
    }

    public String getNewSecondName() {
        return TestDataReader.getTestData(NEW_SECOND_NAME);
    }
}
