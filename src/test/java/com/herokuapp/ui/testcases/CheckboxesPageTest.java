package com.herokuapp.ui.testcases;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.base.TestBase;
import com.herokuapp.pages.CheckboxesPage;
import com.util.TestUtil;

/**
 * Test Suite : Heroku – Checkboxes Page
 * URL        : https://the-internet.herokuapp.com/checkboxes
 *
 * Default state: Checkbox 1 = unchecked | Checkbox 2 = checked
 */
public class CheckboxesPageTest extends TestBase {

    Logger log;
    CheckboxesPage checkboxesPage;

    public CheckboxesPageTest() {
        super();
        log = Logger.getLogger(CheckboxesPageTest.class);
    }

    @BeforeSuite
    public void reportSetup() {
        initializeReport();
    }

    @BeforeMethod
    public void setup() throws MalformedURLException {
        initialization();
        checkboxesPage = new CheckboxesPage();
        checkboxesPage.navigate();
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void defaultCheckboxStateTest() throws IOException {
        /*
         * Checkboxes Page – Test #01: Verify default checkbox states.
         * Steps:
         * 01) Verify Checkbox 1 is unchecked by default.
         * 02) Verify Checkbox 2 is checked by default.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Checkboxes Page – Test #01: Verify default checkbox states.");
        test.assignCategory("Heroku – Checkboxes Page");

        log.info("*".repeat(80));
        log.info("Checkboxes Page – Test #01: Verify default checkbox states.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Verify Checkbox 1 is unchecked by default.");
            test.log(Status.INFO, "Step 01: Verify Checkbox 1 is unchecked by default.");
            Assert.assertFalse(checkboxesPage.isChecked(0), "Checkbox 1 should be unchecked by default.");

            log.info("Step 02: Verify Checkbox 2 is checked by default.");
            test.log(Status.INFO, "Step 02: Verify Checkbox 2 is checked by default.");
            Assert.assertTrue(checkboxesPage.isChecked(1), "Checkbox 2 should be checked by default.");

            test.log(Status.PASS, "Checkboxes Page – Test #01: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Checkboxes Page – Test #01: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Checkboxes Page – Test #01: FAILED – " + e.getMessage());
            throw e;
        }
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void toggleCheckboxTest() throws IOException {
        /*
         * Checkboxes Page – Test #02: Verify toggling Checkbox 1 checks it.
         * Steps:
         * 01) Toggle Checkbox 1 (was unchecked).
         * 02) Verify Checkbox 1 is now checked.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Checkboxes Page – Test #02: Verify toggling Checkbox 1 checks it.");
        test.assignCategory("Heroku – Checkboxes Page");

        log.info("*".repeat(80));
        log.info("Checkboxes Page – Test #02: Verify checkbox toggle.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Toggle Checkbox 1 (initially unchecked).");
            test.log(Status.INFO, "Step 01: Toggle Checkbox 1 (initially unchecked).");
            checkboxesPage.toggleCheckbox(0);

            log.info("Step 02: Verify Checkbox 1 is now checked.");
            test.log(Status.INFO, "Step 02: Verify Checkbox 1 is now checked.");
            Assert.assertTrue(checkboxesPage.isChecked(0),
                    "Checkbox 1 should be checked after toggle.");

            test.log(Status.PASS, "Checkboxes Page – Test #02: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Checkboxes Page – Test #02: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Checkboxes Page – Test #02: FAILED – " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
        log.info("Browser closed.");
    }

    @AfterSuite
    public void reportClosure() {
        if (extent != null) {
            extent.flush();
        }
    }
}
