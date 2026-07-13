package com.herokuapp.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.base.TestBase;

/**
 * Page Object for: https://the-internet.herokuapp.com/dynamic_loading/1
 * Element hidden on page — revealed after clicking Start.
 */
public class DynamicLoadingPage extends TestBase {

    // ── Page path ────────────────────────────────────────────────────────────
    public static final String PAGE_PATH = "/dynamic_loading/1";

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By pageHeading  = By.tagName("h3");
    private final By startButton  = By.cssSelector("#start button");
    private final By finishText   = By.cssSelector("#finish h4");
    private final By loadingSpinner = By.id("loading");

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

    public void clickStartButton() {
        driver.findElement(startButton).click();
    }

    /**
     * Waits up to {@code timeoutSeconds} for the finish element to become
     * visible, then returns its text (e.g., "Hello World!").
     */
    public String getFinishText(int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        wait.until(ExpectedConditions.visibilityOfElementLocated(finishText));
        return driver.findElement(finishText).getText();
    }
}
