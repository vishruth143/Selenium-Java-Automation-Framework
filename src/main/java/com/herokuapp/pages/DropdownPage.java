package com.herokuapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.base.TestBase;

/**
 * Page Object for: https://the-internet.herokuapp.com/dropdown
 */
public class DropdownPage extends TestBase {

    // ── Page path ────────────────────────────────────────────────────────────
    public static final String PAGE_PATH = "/dropdown";

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By dropdownSelect = By.id("dropdown");
    private final By pageHeading    = By.tagName("h3");

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

    public void selectByVisibleText(String text) {
        new Select(driver.findElement(dropdownSelect)).selectByVisibleText(text);
    }

    public void selectByValue(String value) {
        new Select(driver.findElement(dropdownSelect)).selectByValue(value);
    }

    public String getSelectedOptionText() {
        return new Select(driver.findElement(dropdownSelect)).getFirstSelectedOption().getText();
    }
}
