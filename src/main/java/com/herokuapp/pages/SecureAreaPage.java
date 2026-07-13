package com.herokuapp.pages;

import org.openqa.selenium.By;

import com.base.TestBase;

/**
 * Page Object for: https://the-internet.herokuapp.com/secure
 * Landed on after a successful login.
 */
public class SecureAreaPage extends TestBase {

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By flashMessage = By.id("flash");
    private final By pageHeading  = By.tagName("h2");
    private final By logoutButton = By.cssSelector("a[href='/logout']");

    // ── Actions ───────────────────────────────────────────────────────────────
    public String getTitle() {
        return driver.getTitle();
    }

    public String getPageHeading() {
        return driver.findElement(pageHeading).getText();
    }

    public String getFlashMessage() {
        return driver.findElement(flashMessage).getText();
    }

    public boolean isFlashMessageDisplayed() {
        return driver.findElement(flashMessage).isDisplayed();
    }

    public LoginPage logout() {
        driver.findElement(logoutButton).click();
        return new LoginPage();
    }
}
