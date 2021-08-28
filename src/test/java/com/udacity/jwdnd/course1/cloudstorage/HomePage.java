package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "save-changes-note-button")
    private WebElement saveChangesNoteButton;

    @FindBy(id = "nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id = "edit-note-button")
    private WebElement editNoteButton;

    @FindBy(id = "delete-note-button")
    private WebElement deleteNoteButton;

    @FindBy(id = "add-credential-button")
    private WebElement addCredentialButton;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(id = "save-credential-button")
    private WebElement saveCredentialButton;

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;

    @FindBy(id = "edit-credential-button")
    private WebElement editCredentialButton;

    @FindBy(id = "delete-credential-button")
    private WebElement deleteCredentialButton;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void addNote(String title, String description) {
        clickNavNotesTab();
        this.addNoteButton.click();
        this.noteTitleInput.sendKeys(title);
        this.noteDescriptionInput.sendKeys(description);
        this.saveChangesNoteButton.click();
    }

    public void editNote(String title, String description) {
        clickNavNotesTab();
        this.editNoteButton.click();
        this.noteTitleInput.clear();
        this.noteDescriptionInput.clear();
        this.noteTitleInput.sendKeys(title);
        this.noteDescriptionInput.sendKeys(description);
        this.saveChangesNoteButton.click();
    }

    public void deleteNote() {
        clickNavNotesTab();
        this.deleteNoteButton.click();
    }

    public void clickNavNotesTab() {
        this.navNotesTab.click();
    }

    public void clickNavCredentialTab() {
        this.navCredentialsTab.click();
    }

    public void addNewCredential(String url, String username, String password) {
        clickNavCredentialTab();
        this.addCredentialButton.click();
        this.credentialUrlInput.sendKeys(url);
        this.credentialUsernameInput.sendKeys(username);
        this.credentialPasswordInput.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public void editCredential(String url, String username, String password) {
        clickNavCredentialTab();
        this.editCredentialButton.click();
        this.credentialUrlInput.clear();
        this.credentialUrlInput.sendKeys(url);
        this.credentialUsernameInput.clear();
        this.credentialUsernameInput.sendKeys(username);
        this.credentialPasswordInput.clear();
        this.credentialPasswordInput.sendKeys(password);
        this.saveCredentialButton.click();
    }

    public void deleteCredential() {
        clickNavCredentialTab();
        this.deleteCredentialButton.click();
    }

    public String getCredentialPasswordInModal() {
        return credentialPasswordInput.getAttribute("value");
    }
}
