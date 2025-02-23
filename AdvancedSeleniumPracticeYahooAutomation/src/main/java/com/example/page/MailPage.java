package com.example.page;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class MailPage extends AbstractPage {
    private final Logger logger = LoggerFactory.getLogger(MailPage.class);
//////////
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
////////////////
    private static final String CREATE_BUTTON_LOCATOR = "//a[text()='Съставяне']";
    private static final String MESSAGE_TO_FIELD_LOCATOR = "#message-to-field";
    private static final String SUBJECT_FIELD_LOCATOR = "//input[@placeholder='Тема']";
    private static final String TEXT_FIELD_LOCATOR = "//div[@aria-label='Основен текст']";
    private static final String SEND_BUTTON_LOCATOR = "//button[@data-test-id='compose-send-button']";
    private static final String SENT_LOCATOR = "//a[@data-test-folder-name='Sent']";
    private static final String SENT_MENU_LOCATOR = "//button[@data-test-id='selection-controls-menu-button']";
    private static final String SELECT_ALL_SENT_LETTERS_LOCATOR = "//button[@data-test-id='select-all-btn']";
    private static final String NUMBER_SENT_LETTERS_LOCATOR = "//div[@id='announcedSelectionCount']/span";
    private static final String DRAFT_BOX_LOCATOR = "//div[@data-test-folder-container='Draft']/a";
    private static final String CLOSE_AND_MAKE_A_DRAFT_LOCATOR = "//button[@aria-label='Затваряне (и записване като чернова)']";
    private static final String SEND_A_DRAFT_LOCATOR = "//button[@title='Изпращане на имейла']";
    private static final String SUBJECT_VALUE_LOCATOR = "//input[@aria-label='Тема']";
    private static final String EMAIL_VALUE_LOCATOR = "//div[@data-test-id='pill-avatar']/following-sibling::*[1]";
    private static final String TEXT_VALUE_LOCATOR = "//div[text()='Draft 1 here']";
    private static final String ACCOUNT_MENU_OPENER_LOCATOR = "span._yb_1v7sa8z._yb_suiwtg._yb_1mpmhvp";
    private static final String EXIT_BUTTON_LOCATOR = "//span[text()='Излизане']";
    private static final String DRAFT_CONTAINER_LOCATOR = "//div[@data-test-id='virtual-list']/ul";
    private static final String ALL_DRAFTS_CHECKBOX_LOCATOR = "//button[@data-test-id='checkbox']";
    private static final String NUMBER_OF_DRAFTS_LOCATOR = "//div[@id='announcedSelectionCount']/span";
    private static final String LETTER_WITHOUT_THEME_LOCATOR = "//div[text()='Искате ли да изпратите съобщението без тема?']";

    @FindBy(how = How.XPATH, using = CREATE_BUTTON_LOCATOR)
    public WebElement createButton;

    @FindBy(how = How.CSS, using = MESSAGE_TO_FIELD_LOCATOR)
    public WebElement messageToField;

    @FindBy(how = How.XPATH, using = SUBJECT_FIELD_LOCATOR)
    public WebElement subjectField;

    @FindBy(how = How.XPATH, using = TEXT_FIELD_LOCATOR)
    public WebElement textField;

    @FindBy(how = How.XPATH, using = SEND_BUTTON_LOCATOR)
    public WebElement sendButton;

    @FindBy(how = How.XPATH, using = SENT_LOCATOR)
    public WebElement sent;

    @FindBy(how = How.XPATH, using = SENT_MENU_LOCATOR)
    public WebElement sentMenu;

    @FindBy(how = How.XPATH, using = SELECT_ALL_SENT_LETTERS_LOCATOR)
    public WebElement selectAllSentLetters;

    @FindBy(how = How.XPATH, using = NUMBER_SENT_LETTERS_LOCATOR)
    public WebElement numberSentLetters;

    @FindBy(how = How.XPATH, using = DRAFT_BOX_LOCATOR)
    public WebElement draftBox;

    @FindBy(how = How.XPATH, using = CLOSE_AND_MAKE_A_DRAFT_LOCATOR)
    public WebElement closeAndMakeADraft;

    @FindBy(how = How.XPATH, using = SEND_A_DRAFT_LOCATOR)
    public WebElement sendADraft;

    @FindBy(how = How.XPATH, using = SUBJECT_VALUE_LOCATOR)
    public WebElement subjectValue;

    @FindBy(how = How.XPATH, using = EMAIL_VALUE_LOCATOR)
    public WebElement emailValue;

    @FindBy(how = How.XPATH, using = TEXT_VALUE_LOCATOR)
    public WebElement textValue;

    @FindBy(how = How.CSS, using = ACCOUNT_MENU_OPENER_LOCATOR)
    public WebElement accountMenuOpener;

    @FindBy(how = How.XPATH, using = EXIT_BUTTON_LOCATOR)
    public WebElement exitButton;

    @FindBy(how = How.XPATH, using = DRAFT_CONTAINER_LOCATOR)
    public WebElement draftContainer;

    @FindBy(how = How.XPATH, using = ALL_DRAFTS_CHECKBOX_LOCATOR)
    public WebElement allDraftsCheckbox;

    @FindBy(how = How.XPATH, using = NUMBER_OF_DRAFTS_LOCATOR)
    public WebElement numberOfDrafts;

    @FindBy(how = How.XPATH, using = LETTER_WITHOUT_THEME_LOCATOR)
    public WebElement letterWithoutTheme;


    public MailPage(WebDriver driver) {
        super(driver);
    }

    /**
     * Populates the fields required to send an email: "To", "Subject", and "Body".
     * This method performs the following actions:
     * 1. Waits for the "To" field to be visible, then clicks and types the recipient's email address.
     * 2. Clicks on the body of the email to move focus away from the "To" field.
     * 3. Waits for the "Subject" field to be visible, then clicks and types the subject of the email.
     * 4. Waits for the email body field to be visible, then clicks and types the message text.
     *
     * @param sendToField the email address of the recipient to be entered in the "To" field.
     * @param subject the subject of the email to be entered in the "Subject" field.
     * @param text the body of the email to be entered in the "Text" field.
     */
    public void fillEmailForm(String sendToField, String subject, String text) {
        enterTextWhenVisible(messageToField, sendToField);
        body.click();

        enterTextWhenVisible(subjectField, subject);
        enterTextWhenVisible(textField, text);
    }
    private void enterTextWhenVisible(WebElement element, String text) {
        waitForElementToBeVisible(element);
        element.click();
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Creates and sends an email by populating the "To", "Subject", and "Body" fields and clicking the necessary buttons.
     * This method automates the process of composing and sending an email.
     * The following actions are performed:
     * 1. Waits for the "Create" button to be clickable, then clicks on it to open the email creation form.
     * 2. Populates the email fields (recipient, subject, and body) by calling the {@link #fillEmailForm(String, String, String)} method.

     * @param sendToField the email address of the recipient to be entered in the "To" field.
     * @param subject the subject of the email to be entered in the "Subject" field.
     * @param text the body of the email to be entered in the "Text" field.
     *
     * @throws RuntimeException if an error occurs during the email creation or sending process.
     */
    public void createEmail(String sendToField, String subject, String text) {
        logger.debug("Entering createEmail() with parameters sendToField: {}, subject: {}, text: {}", sendToField, subject, text);
       try {
        createButton.click();
        logger.debug("Clicked on the 'Create' button.");

        fillEmailForm(sendToField, subject, text);

    } catch (Exception e) {
        logger.error("An error occurred while creating the email: {}", e.getMessage());
        throw new RuntimeException("Failed to create email", e);
    }
}
    /**
     *   Waits for the "Send" button to be visible, then clicks on it to send the email.
     *   Refreshes the sent box after the email is sent to confirm the action was completed.
     *
    */
    public void sendEmail() {
        try {
            waitForElementToBeVisible(sendButton);
            sendButton.click();
            logger.debug("Clicked on the 'Send' button.");
            refreshBox(sent);
        } catch (Exception e) {
            logger.error("An error occurred while sending the email: {}", e.getMessage());
            throw new RuntimeException("Failed to send email", e);
        }
    }

    /**
     * Retrieves the number of sent letters by interacting with the sent box UI.
     * This method automates the process of checking the number of sent letters by performing the following actions:
     * - Refreshes the sent box.
     * - Clicks on the sent menu to open the list of sent letters.
     * - Waits for the "Select All" button for sent letters to be visible and clicks it.
     * - Waits for the element displaying the number of sent letters to be visible and retrieves its text.
     * - Parses the number of sent letters from the element text and returns the value.
     *
     * @return the number of sent letters as an integer.
     *
     * @throws RuntimeException if an error occurs while interacting with the sent box or retrieving the number of sent letters.
     */
    public int getNumberOfSentLetters(){
        try {
            refreshBox(sent);
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

    /**
     * Creates a draft email by populating the "To", "Subject", and "Body" fields and clicking the necessary buttons.
     * This method automates the process of composing an email, saving it as a draft, and refreshing the draft box.
     * The method performs the following actions:
     * 1. Waits for the "Create" button to be clickable, then clicks on it to open the email creation form.
     * 2. Populates the email fields (recipient, subject, and text) by calling the {@link #fillEmailForm(String, String, String)} method.
     * 3. Waits for the "Close and Make a Draft" button to be visible, then clicks on it to save the email as a draft.
     * 4. Refreshes the draft box to confirm the action was completed.
     *
     * @param sendToField the email address of the recipient to be entered in the "To" field.
     * @param subject the subject of the email to be entered in the "Subject" field.
     * @param text the body of the email to be entered in the "Text" field.
     *
     * @throws RuntimeException if an error occurs during the draft creation process.
     */
    public void createDraft(String sendToField, String subject, String text) {
     try{
//        waitForElementToBeClickable(createButton);
//         wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("modal-outer")));
        createButton.click();

        fillEmailForm(sendToField, subject, text);

        waitForElementToBeVisible(closeAndMakeADraft);
        closeAndMakeADraft.click();
        logger.debug("Clicked on 'Close and Make a Draft' button.");

         refreshBox(draftBox);
         logger.debug("Draft email created successfully and draft box refreshed.");
     } catch (Exception e) {
            logger.error("An error occurred while creating the draft email: {}", e.getMessage());
            throw new RuntimeException("Failed to create the draft email", e);
        }
    }

    /**
     * Refreshes the specified email box by clicking on the provided {@link WebElement} representing the box.
     * This method waits for the box element to become clickable and then performs the click operation to refresh the box.
     * If the box is not clickable within the specified timeout, an error is logged, and a {@link RuntimeException} is thrown.
     *
     * @param boxElement the {@link WebElement} representing the email box to be refreshed (e.g., draft box, sent box).
     *
     * @throws RuntimeException if the element is not clickable within the timeout or if an unexpected error occurs during the refresh process.
     */
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

    public WebElement getSentFolderElement(){
        return sent;
    }
    public WebElement getDraftBoxElement(){
        return draftBox;
    }

    public void refreshDraftCheckboxBox(){
        String areaChecked = allDraftsCheckbox.getAttribute("aria-checked");
        logger.debug("Current 'aria-checked' state: {}", areaChecked);
        refreshBox(allDraftsCheckbox);
        logger.debug("Draft checkbox refreshed.");
    }

    /**
     * Retrieves the number of draft emails by checking the "All Drafts" checkbox and parsing the draft count from the UI.
     * This method performs the following actions:
     * <ul>
     *     <li>Refreshes the draft box by clicking on the provided draft box element.</li>
     *     <li>Checks the state of the "All Drafts" checkbox to ensure it is selected, and clicks it if not.</li>
     *     <li>Waits for the draft count to become visible and retrieves the number of drafts displayed.</li>
     * </ul>
     *
     * <p>If any issues are encountered during the process (e.g., element not found or parsing error), the method logs the error and returns 0.</p>
     *
     * @return the number of drafts as an integer, or 0 if an error occurs.
     */
    public int getNumberOfDrafts(){
        try {
            refreshBox(draftBox);
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

    /**
     * Opens a draft email for sending by selecting the second draft from the draft list.
     * This method performs the following actions:
     * - Clicks the draft box to reveal the list of drafts.
     * - Waits for the draft container to be visible.
     * - Checks if there are any drafts available in the list.
     * - If drafts are available, it opens the second draft by clicking on it.
     * - If no drafts are found, logs a message indicating no drafts are available to open.
     * If any issues are encountered during the process (e.g., element not found or interaction issue), the method logs the error.
     */
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

    /**
     * Sends the latest draft email by interacting with the "Send Draft" button.
     * This method performs the following actions:
     * - Waits for the "Send Draft" button to be visible.
     * - Clicks the "Send Draft" button to send the latest draft.
     * - Refreshes the draft box to reflect the updated status after sending the draft.
     * If any issues are encountered during the process (e.g., element not found, button not interactable, or any other error), the method logs the error.
     */
    public void sendLatestDraft(){
        try {
            waitForElementToBeVisible(sendADraft);
            sendADraft.click();
            refreshBox(draftBox);
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
    public String sendALetterWithoutTheme(){
        waitForElementToBeVisible(letterWithoutTheme);
        return letterWithoutTheme.getText();
    }

    public String getExpectedThemeWarning() {
        return "warning.expectedThemeWarning";
    }
}
