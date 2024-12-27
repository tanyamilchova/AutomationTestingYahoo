package com.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

public class MailPage extends AbstractPage{
    private final Logger logger = LoggerFactory.getLogger(MailPage.class);

    @FindBy(how = How.XPATH, using = "//a[text()='Съставяне']")
    public WebElement createButton;
    @FindBy(how = How.CSS, using = "#message-to-field")
    public WebElement messageToField;
    @FindBy(how = How.XPATH, using = "//input[@placeholder='Тема']")
    public WebElement subgectField;
    @FindBy(how = How.XPATH, using = "//div[@aria-label='Основен текст']")
    public WebElement textField;
    @FindBy(how = How.XPATH, using = "//button[@data-test-id='compose-send-button']")
    public WebElement sendButton;
    @FindBy(how = How.XPATH, using = "//a[@data-test-folder-name='Sent']")
    public WebElement sent;
    @FindBy(how = How.XPATH, using = "//button[@data-test-id='selection-controls-menu-button']")
    public WebElement sentMenu;

    @FindBy(how = How.XPATH, using = "//button[@data-test-id='select-all-btn']")
    public WebElement selectAllSentLetters;
    @FindBy(how = How.XPATH, using = "//div[@id='announcedSelectionCount']/span")
    public WebElement numberSentLetters;


    @FindBy(how = How.XPATH, using = "//div[@data-test-folder-container='Draft']/a")
    public WebElement draftBox;
    @FindBy(how = How.XPATH, using = "//button[@aria-label='Затваряне (и записване като чернова)']")
    public WebElement closeAndMakeADraft;
    @FindBy(how = How.XPATH, using = "//button[@title='Изпращане на имейла']")
    public WebElement sendADraft;
    @FindBy(how = How.XPATH, using = "//input[@aria-label='Тема']")
    public WebElement subjectValue;
    @FindBy(how = How.XPATH, using = "//div[@data-test-id='pill-avatar']/following-sibling::*[1]")
    public WebElement emailValue;
    @FindBy(how = How.XPATH, using = "//div[text()='Draft 1 here']")
    public WebElement textValue;
    @FindBy(how = How.CSS, using = "span._yb_1v7sa8z._yb_suiwtg._yb_1mpmhvp")
    WebElement accountMenuOpener;
    @FindBy(how = How.XPATH, using = "//span[text()='Излизане']")
    WebElement exitButton;
    @FindBy(how = How.XPATH, using = "//body")
    WebElement body;
    @FindBy(how = How.XPATH, using = "//div[@data-test-id='virtual-list']/ul")
    WebElement draftContainer;
    @FindBy(how = How.XPATH, using = "//button[@data-test-id='checkbox']")
    WebElement allDraftsCheckbox;
    @FindBy(how = How.XPATH, using = "//div[@id='announcedSelectionCount']/span")
    WebElement numberOfDrafts;


    public MailPage(WebDriver driver) {
        super(driver);
    }

    public void createEmail(String sendToField, String subject, String text) {
        logger.debug("Entering createEmail() with parameters sendToField: {}, subject: {}, text: {}", sendToField, subject, text);
       try {
        waitForElementToBeClickable(createButton);
        createButton.click();
        logger.debug("Clicked on the 'Create' button.");

        waitForElementToBeVisible(messageToField);
        messageToField.click();
        messageToField.sendKeys(sendToField);
        logger.debug("Entered recipient email: {}", sendToField);

        body.click();

        waitForElementToBeVisible(subgectField);
        subgectField.click();
        subgectField.sendKeys(subject);
        logger.debug("Entered subject: {}", subject);

        waitForElementToBeVisible(textField);
        textField.click();
        textField.sendKeys(text);
        logger.debug("Entered email body text.");

        waitForElementToBeVisible(sendButton);
        sendButton.click();
        logger.debug("Clicked on the 'Send' button.");

        refreshLetterBox();
    } catch (Exception e) {
        logger.error("An error occurred while creating the email: {}", e.getMessage());
        throw new RuntimeException("Failed to create email", e);
    }
}

    public int getNumberOfSentLetters(){
        try {
            refreshLetterBox();
            sentMenu.click();


            waitForElementToBeVisible(selectAllSentLetters);
            selectAllSentLetters.click();

            waitForElementToBeVisible(numberSentLetters);
            return Integer.parseInt(numberSentLetters.getText());
        } catch (Exception e) {
            logger.error("An error occurred while getting the number of sent letters: {}", e.getMessage());
            throw new RuntimeException("Failed to get the number of sent letters", e);
        }
    }

    public void createDraft(String sendToField, String subject, String text) {
     try{
        waitForElementToBeClickable(createButton);
        createButton.click();

        waitForElementToBeVisible(messageToField);
        messageToField.click();
        messageToField.sendKeys(sendToField);
        logger.debug("Created recipient email: {}", sendToField);
        body.click();

        waitForElementToBeVisible(subgectField);
        subgectField.click();
        subgectField.sendKeys(subject);
        logger.debug("Created subject: {}", subject);

        waitForElementToBeVisible(textField);
        textField.click();
        textField.sendKeys(text);
        logger.debug("Created body text for draft.");

        waitForElementToBeVisible(closeAndMakeADraft);
        closeAndMakeADraft.click();
        logger.debug("Clicked on 'Close and Make a Draft' button.");

        refreshDraftBox();
         logger.debug("Draft email created successfully and draft box refreshed.");
     } catch (Exception e) {
            logger.error("An error occurred while creating the draft email: {}", e.getMessage());
            throw new RuntimeException("Failed to create the draft email", e);
        }
    }

    public void refreshBox(WebElement boxElement){
        try {
            waitForElementToBeClickable(boxElement);
            boxElement.click();
        } catch (TimeoutException e) {
            logger.error("Timeout: The draft box element was not clickable within the given time.", e);
            throw new RuntimeException("Draft box click operation timed out.", e);
        } catch (Exception e) {
            logger.error("An unexpected error occurred while refreshing the draft box.", e);
            throw new RuntimeException("Failed to refresh draft box.", e);
        }
    }

    public void refreshDraftBox(){
        refreshBox(draftBox);
    }

    public void refreshLetterBox(){
        refreshBox(sent);
    }

    public void refreshDraftCheckboxBox(){
        String areaChecked = allDraftsCheckbox.getAttribute("aria-checked");
        logger.debug("Current 'aria-checked' state: {}", areaChecked);
        refreshBox(allDraftsCheckbox);
        logger.debug("Draft checkbox refreshed.");
    }

    public int getNumberOfDrafts(){
        try {
            refreshDraftBox();
            logger.debug("Draft box refreshed.");
            waitForElementToBeClickable(allDraftsCheckbox);
            String areaChecked = allDraftsCheckbox.getAttribute("aria-checked");
            logger.debug("The 'All Drafts' checkbox state is: {}", areaChecked);
            if(areaChecked.equals("false")) {
                logger.debug("The 'All Drafts' checkbox is not selected. Clicking to select.");
                allDraftsCheckbox.click();
            }
            waitForElementToBeVisible(numberOfDrafts);
            int draftCount = Integer.parseInt(numberOfDrafts.getText());
            logger.debug("Draft count retrieved: {}", draftCount);
            return draftCount;

        } catch (NoSuchElementException e) {
            logger.error("Draft count element not found: {}", e.getMessage());
            return 0;
        } catch (NumberFormatException e) {
            logger.error("Failed to parse draft count as integer: {}", e.getMessage());
            return 0;
        } catch (Exception e) {
            logger.error("Unexpected error occurred while getting draft count: {}", e.getMessage());
            return 0;
        }
    }

    public void openDraftToSend() {
        try {
            draftBox.click();
            waitForElementToBeVisible(draftContainer);
            List<WebElement> draftList = draftContainer.findElements(By.tagName("li"));
            logger.debug("Number of drafts found: {}", draftList.size());

            if (!draftList.isEmpty()) {
                WebElement draft = draftList.get(1);
                logger.debug("Opening draft: {}", draft.getText());
                draft.click();
            } else {
                logger.debug("No drafts available to open.");
            }
        } catch (NoSuchElementException e) {
            logger.error("Open Draft button not found: {}", e.getMessage());
        } catch (ElementNotInteractableException e) {
            logger.error("Failed to interact with the Open Draft button: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while opening the draft: {}", e.getMessage());
        }
    }

    public void sendLatestDraft(){
        try {
            waitForElementToBeVisible(sendADraft);
            sendADraft.click();
            refreshDraftBox();
        } catch (NoSuchElementException e) {
            logger.error("Send Draft button not found: {}", e.getMessage());
        } catch (ElementNotInteractableException e) {
            logger.error("Failed to interact with 'Send Draft' button: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while sending the draft: {}", e.getMessage());
        }
    }

    public String getSubjectValue(){
        try {
            waitForElementToBeVisible(subjectValue);
            return subjectValue.getAttribute("value");
        } catch (NoSuchElementException e) {
            logger.error("Subject element not found: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving subject value: {}", e.getMessage());
            return "";
        }
    }

    public String getEmailValue(){
        try {
            waitForElementToBeVisible(emailValue);
            return emailValue.getText();
        } catch (NoSuchElementException e) {
            logger.error("Email element not found: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving email value: {}", e.getMessage());
            return "";
        }
    }

    public String getTextValue(){
        try {
            waitForElementToBeVisible(textValue);
            return textValue.getText();
        } catch (NoSuchElementException e) {
            logger.error("Text element not found: {}", e.getMessage());
            return "";
        } catch (Exception e) {
            logger.error("Unexpected error occurred while retrieving text value: {}", e.getMessage());
            return "";
        }
    }

    public void exitAccount(){
        try {
            accountMenuOpener.click();
            waitForElementToBeClickable(exitButton);
            exitButton.click();
            logger.info("Successfully logged out of the account.");
        } catch (NoSuchElementException e) {
            logger.error("Account menu or exit button not found: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error occurred while logging out: {}", e.getMessage());
        }
    }
}
