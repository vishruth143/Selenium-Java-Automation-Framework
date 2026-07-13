package com.herokuapp.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.base.TestBase;

/**
 * Page Object for: https://the-internet.herokuapp.com/checkboxes
 * Checkbox 1 — unchecked by default.
 * Checkbox 2 — checked by default.
 */
public class CheckboxesPage extends TestBase {

    // ── Page path ────────────────────────────────────────────────────────────
    public static final String PAGE_PATH = "/checkboxes";

    // ── Locators ─────────────────────────────────────────────────────────────
    private final By checkboxes  = By.cssSelector("form#checkboxes input[type='checkbox']");
    private final By pageHeading = By.tagName("h3");

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

    /**
     * Returns all checkbox elements (0-indexed).
     */
    public List<WebElement> getCheckboxes() {
        return driver.findElements(checkboxes);
    }

    /**
     * Returns true if the checkbox at the given 0-based index is checked.
     */
    public boolean isChecked(int index) {
        return getCheckboxes().get(index).isSelected();
    }

    /**
     * Clicks (toggles) the checkbox at the given 0-based index.
     */
    public void toggleCheckbox(int index) {
        getCheckboxes().get(index).click();
    }
}
