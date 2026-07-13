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
import com.herokuapp.pages.DropdownPage;
import com.util.TestUtil;

/**
 * Test Suite : Heroku – Dropdown Page
 * URL        : https://the-internet.herokuapp.com/dropdown
 */
public class DropdownPageTest extends TestBase {

    Logger log;
    DropdownPage dropdownPage;

    public DropdownPageTest() {
        super();
        log = Logger.getLogger(DropdownPageTest.class);
    }

    @BeforeSuite
    public void reportSetup() {
        initializeReport();
    }

    @BeforeMethod
    public void setup() throws MalformedURLException {
        initialization();
        dropdownPage = new DropdownPage();
        dropdownPage.navigate();
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void dropdownPageHeadingTest() throws IOException {
        /*
         * Dropdown Page – Test #01: Verify the 'Dropdown List' heading.
         * Steps:
         * 01) Navigate to the Dropdown page.
         * 02) Verify the page heading is 'Dropdown List'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Dropdown Page – Test #01: Verify the 'Dropdown List' heading.");
        test.assignCategory("Heroku – Dropdown Page");

        log.info("*".repeat(80));
        log.info("Dropdown Page – Test #01: Verify the 'Dropdown List' heading.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Verify the page heading is 'Dropdown List'.");
            test.log(Status.INFO, "Step 01: Verify the page heading is 'Dropdown List'.");
            Assert.assertEquals(dropdownPage.getPageHeading(), "Dropdown List",
                    "Dropdown page heading does not match.");

            test.log(Status.PASS, "Dropdown Page – Test #01: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dropdown Page – Test #01: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dropdown Page – Test #01: FAILED – " + e.getMessage());
            throw e;
        }
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void selectDropdownOptionTest() throws IOException {
        /*
         * Dropdown Page – Test #02: Verify selection of 'Option 1' and 'Option 2'.
         * Steps:
         * 01) Select 'Option 1' and verify.
         * 02) Select 'Option 2' and verify.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Dropdown Page – Test #02: Verify selection of 'Option 1' and 'Option 2'.");
        test.assignCategory("Heroku – Dropdown Page");

        log.info("*".repeat(80));
        log.info("Dropdown Page – Test #02: Verify dropdown option selection.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Select 'Option 1' and verify.");
            test.log(Status.INFO, "Step 01: Select 'Option 1' and verify.");
            dropdownPage.selectByVisibleText("Option 1");
            Assert.assertEquals(dropdownPage.getSelectedOptionText(), "Option 1",
                    "'Option 1' was not selected.");

            log.info("Step 02: Select 'Option 2' and verify.");
            test.log(Status.INFO, "Step 02: Select 'Option 2' and verify.");
            dropdownPage.selectByVisibleText("Option 2");
            Assert.assertEquals(dropdownPage.getSelectedOptionText(), "Option 2",
                    "'Option 2' was not selected.");

            test.log(Status.PASS, "Dropdown Page – Test #02: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dropdown Page – Test #02: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dropdown Page – Test #02: FAILED – " + e.getMessage());
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
