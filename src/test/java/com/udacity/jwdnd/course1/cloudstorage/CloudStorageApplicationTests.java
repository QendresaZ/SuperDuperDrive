package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Base64;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static WebDriver driver;

	private String baseUrl;

	@BeforeAll
	public static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
		driver = null;
	}

	@BeforeEach
	public void beforeEach() {
		baseUrl = baseUrl = "http://localhost:" + port;
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void testUnauthorizedAccess() {
		driver.get(baseUrl + "/home");
		driver.get(baseUrl + "/logout");
		assertEquals(baseUrl + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testUserSignupLoginHomeLogout(){
		String firstname = "qeqa";
		String lastname = "test";
		String username = "qeqatest";
		String password = "test123";

		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);
		assertTrue(signupPage.containsSuccessMsg("You successfully signed up!"));

		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
		assertEquals(baseUrl + "/home", driver.getCurrentUrl());

		driver.get(baseUrl + "/home");
		driver.findElement(By.id("logoutButton")).click();
		assertEquals(baseUrl + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testUserAddingEditingDeletingNote() throws InterruptedException {
		String noteTitle = "testNoteTitle";
		String noteDescription = "testNoteDescription";
		driver.get(baseUrl + "/login");
		WebDriverWait wait;

		login();

		HomePage homePage = new HomePage(driver);
		homePage.addNote(noteTitle, noteDescription);
		homePage.clickNavNotesTab();

		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#notesTable > tbody > tr > td:nth-child(2)")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#notesTable > tbody > tr > td:nth-child(3)")));
		String noteTitleInTable = driver.findElement(By.cssSelector("#notesTable > tbody > tr > td:nth-child(2)")).getText();
		String noteDescriptionInTable = driver.findElement(By.cssSelector("#notesTable > tbody > tr > td:nth-child(3)")).getText();
		assertTrue(noteTitleInTable.equals(noteTitle));
		assertTrue(noteDescriptionInTable.equals(noteDescription));

		String editedNoteTitle = "testEditedNoteTitle";
		String editedNoteDescription = "test Edited Note Description";

		homePage.editNote(editedNoteTitle, editedNoteDescription);
		homePage.clickNavNotesTab();
		wait = new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#notesTable > tbody > tr > td:nth-child(2)")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#notesTable > tbody > tr > td:nth-child(3)")));
		String editedNoteTitleInTable = driver.findElement(By.cssSelector("#notesTable > tbody > tr > td:nth-child(2)")).getText();
		String editedNoteDescriptionInTable = driver.findElement(By.cssSelector("#notesTable > tbody > tr > td:nth-child(3)")).getText();
		assertTrue(editedNoteTitleInTable.equals(editedNoteTitle));
		assertTrue(editedNoteDescriptionInTable.equals(editedNoteDescription));


		homePage.deleteNote();
		homePage.clickNavNotesTab();
		wait = new WebDriverWait(driver,30);
		boolean deletedNote = driver.findElement(By.cssSelector("#notesTable > tbody")).getText().isEmpty();
		assertTrue(deletedNote);
	}

	@Test
	public void testUserAddingEditingDeletingCredential() {
		driver.get(baseUrl + "/login");
		login();
		WebDriverWait wait;

		String url = "testurl.com";
		String username = "test";
		String password = "test123";

		HomePage homePage = new HomePage(driver);
		homePage.addNewCredential(url, username, password);
		homePage.clickNavCredentialTab();
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("url-field")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username-field")));
		assertEquals(url, driver.findElement(By.id("url-field")).getText());
		assertEquals(username, driver.findElement(By.id("username-field")).getText());
		//  Check if can be decoded - means it was encrypted
		assertNotNull(Base64.getDecoder().decode(driver.findElement(By.id("pass-field")).getText()));

		String editedUrl = "edited-url.com";
		String editedUsername = "Edited Username";
		String editedPass = "Edited-pass";

		homePage.editCredential(editedUrl, editedUsername, editedPass);
		homePage.clickNavCredentialTab();
		wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("url-field")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username-field")));

		assertEquals(editedUrl, driver.findElement(By.id("url-field")).getText());
		assertEquals(editedUsername, driver.findElement(By.id("username-field")).getText());
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-button")));
		driver.findElement(By.id("edit-credential-button")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialModal")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		assertEquals(editedPass, homePage.getCredentialPasswordInModal());

		driver.findElement(By.id("close-credential-modal-btn")).click();
		homePage.deleteCredential();
		homePage.clickNavCredentialTab();
		wait = new WebDriverWait(driver, 30);
		boolean deletedCredential = driver.findElement(By.cssSelector("#credentialTable > tbody")).getText().isEmpty();
		assertTrue(deletedCredential);
	}

	private void login() {
		String username = "qeqatest";
		String password = "test123";

		driver.get(baseUrl + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	private void createUser() {
		String firstname = "qeqa";
		String lastname = "test";
		String username = "qeqatest";
		String password = "test123";

		driver.get(baseUrl + "/signup");
		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstname, lastname, username, password);
		assertTrue(signupPage.containsSuccessMsg("You successfully signed up!"));
	}

}
