package com.herokuapp.pages;

import org.openqa.selenium.By;

import com.base.TestBase;

/**
 * Page Object for: https://the-internet.herokuapp.com/login
 */
public class LoginPage extends TestBase {

    // ── Page path ────────────────────────────────────────────────────────────
    public static final String PAGE_PATH = "/login";

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By usernameInput  = By.id("username");
    private final By passwordInput  = By.id("password");
    private final By loginButton    = By.cssSelector("button[type='submit']");
    private final By flashMessage   = By.id("flash");
    private final By pageHeading    = By.tagName("h2");

    // ── Navigation ───────────────────────────────────────────────────────────
    public void navigate() {
        driver.get(prop.getProperty("url") + PAGE_PATH);
    }

    // ── Actions ───────────────────────────────────────────────────────────────
    public String getTitle() {
        return driver.getTitle();
    }

    public String getPageHeading() {
        return driver.findElement(pageHeading).getText();
    }

    public SecureAreaPage login(String username, String password) {
        driver.findElement(usernameInput).clear();
        driver.findElement(usernameInput).sendKeys(username);
        driver.findElement(passwordInput).clear();
        driver.findElement(passwordInput).sendKeys(password);
        driver.findElement(loginButton).click();
        return new SecureAreaPage();
    }

    public String getFlashMessage() {
        return driver.findElement(flashMessage).getText();
    }

    public boolean isFlashMessageDisplayed() {
        return driver.findElement(flashMessage).isDisplayed();
    }
}
