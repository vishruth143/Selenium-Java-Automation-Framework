package com.herokuapp.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

import com.base.TestBase;

/**
 * Page Object for: https://the-internet.herokuapp.com/javascript_alerts
 */
public class JavaScriptAlertsPage extends TestBase {

    // ── Page path ────────────────────────────────────────────────────────────
    public static final String PAGE_PATH = "/javascript_alerts";

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By jsAlertButton   = By.cssSelector("button[onclick='jsAlert()']");
    private final By jsConfirmButton = By.cssSelector("button[onclick='jsConfirm()']");
    private final By jsPromptButton  = By.cssSelector("button[onclick='jsPrompt()']");
    private final By resultText      = By.id("result");
    private final By pageHeading     = By.tagName("h3");

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

    /** Triggers the JS Alert, accepts it, and returns the result message. */
    public String triggerAndAcceptAlert() {
        driver.findElement(jsAlertButton).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return driver.findElement(resultText).getText();
    }

    /** Triggers the JS Confirm, accepts it, and returns the result message. */
    public String triggerAndAcceptConfirm() {
        driver.findElement(jsConfirmButton).click();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return driver.findElement(resultText).getText();
    }

    /** Triggers the JS Confirm, dismisses it, and returns the result message. */
    public String triggerAndDismissConfirm() {
        driver.findElement(jsConfirmButton).click();
        Alert alert = driver.switchTo().alert();
        alert.dismiss();
        return driver.findElement(resultText).getText();
    }

    /** Triggers the JS Prompt, types the given text, accepts, and returns the result message. */
    public String triggerPromptWithText(String text) {
        driver.findElement(jsPromptButton).click();
        Alert alert = driver.switchTo().alert();
        alert.sendKeys(text);
        alert.accept();
        return driver.findElement(resultText).getText();
    }

    public String getResultText() {
        return driver.findElement(resultText).getText();
    }
}
