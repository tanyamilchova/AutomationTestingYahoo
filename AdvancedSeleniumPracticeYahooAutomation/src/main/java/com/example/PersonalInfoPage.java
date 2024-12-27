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


    public void accessAccountInformation(){
        personalInfo.click();
    }

    public String getEmailInfo(){
        waitForElementToBeVisible(emailInfoContainer);
        return  emailInfoContainer.getDomAttribute("title");
    }

    public String getNameInfo(){
        waitForElementToBeVisible(nameInfoContainer);
        return  nameInfoContainer.getText();
    }

    public String getSecondNameInfo(){
        waitForElementToBeVisible(secondNameInfoContainer);
        return  secondNameInfoContainer.getText();
    }

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
}
