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
import com.herokuapp.pages.JavaScriptAlertsPage;
import com.util.TestUtil;

/**
 * Test Suite : Heroku – JavaScript Alerts Page
 * URL        : https://the-internet.herokuapp.com/javascript_alerts
 */
public class JavaScriptAlertsPageTest extends TestBase {

    Logger log;
    JavaScriptAlertsPage jsAlertsPage;

    public JavaScriptAlertsPageTest() {
        super();
        log = Logger.getLogger(JavaScriptAlertsPageTest.class);
    }

    @BeforeSuite
    public void reportSetup() {
        initializeReport();
    }

    @BeforeMethod
    public void setup() throws MalformedURLException {
        log.info("--- [SETUP] Launching browser | Initializing JavaScriptAlertsPage ---");
        initialization();
        jsAlertsPage = new JavaScriptAlertsPage();
        jsAlertsPage.navigate();
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void jsAlertAcceptTest() throws IOException {
        /*
         * JS Alerts Page – Test #01: Verify accepting a JS Alert.
         * Steps:
         * 01) Click the 'Click for JS Alert' button.
         * 02) Accept the alert.
         * 03) Verify the result text is 'You successfuly clicked an alert'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "JS Alerts Page – Test #01: Verify accepting a JS Alert.");
        test.assignCategory("Heroku – JavaScript Alerts Page");

        log.info("*".repeat(80));
        log.info("JS Alerts Page – Test #01: Verify accepting a JS Alert.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Click JS Alert button and accept the alert.");
            test.log(Status.INFO, "Step 01: Click JS Alert button and accept the alert.");
            String result = jsAlertsPage.triggerAndAcceptAlert();

            log.info("Step 02: Verify the result text.");
            test.log(Status.INFO, "Step 02: Verify the result text.");
            Assert.assertEquals(result, "You successfuly clicked an alert",
                    "JS Alert result text does not match.");

            test.log(Status.PASS, "JS Alerts Page – Test #01: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "JS Alerts Page – Test #01: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "JS Alerts Page – Test #01: FAILED – " + e.getMessage());
            throw e;
        }
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void jsConfirmAcceptTest() throws IOException {
        /*
         * JS Alerts Page – Test #02: Verify accepting a JS Confirm dialog.
         * Steps:
         * 01) Click the 'Click for JS Confirm' button.
         * 02) Accept the confirm dialog.
         * 03) Verify the result text is 'You clicked: Ok'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "JS Alerts Page – Test #02: Verify accepting a JS Confirm dialog.");
        test.assignCategory("Heroku – JavaScript Alerts Page");

        log.info("*".repeat(80));
        log.info("JS Alerts Page – Test #02: Verify accepting a JS Confirm dialog.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Click JS Confirm button and accept the dialog.");
            test.log(Status.INFO, "Step 01: Click JS Confirm button and accept the dialog.");
            String result = jsAlertsPage.triggerAndAcceptConfirm();

            log.info("Step 02: Verify the result text is 'You clicked: Ok'.");
            test.log(Status.INFO, "Step 02: Verify the result text is 'You clicked: Ok'.");
            Assert.assertEquals(result, "You clicked: Ok",
                    "JS Confirm accept result text does not match.");

            test.log(Status.PASS, "JS Alerts Page – Test #02: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "JS Alerts Page – Test #02: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "JS Alerts Page – Test #02: FAILED – " + e.getMessage());
            throw e;
        }
    }

    // ── Test #03 ──────────────────────────────────────────────────────────────
    @Test(priority = 3)
    public void jsConfirmDismissTest() throws IOException {
        /*
         * JS Alerts Page – Test #03: Verify dismissing a JS Confirm dialog.
         * Steps:
         * 01) Click the 'Click for JS Confirm' button.
         * 02) Dismiss the confirm dialog.
         * 03) Verify the result text is 'You clicked: Cancel'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "JS Alerts Page – Test #03: Verify dismissing a JS Confirm dialog.");
        test.assignCategory("Heroku – JavaScript Alerts Page");

        log.info("*".repeat(80));
        log.info("JS Alerts Page – Test #03: Verify dismissing a JS Confirm dialog.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Click JS Confirm button and dismiss the dialog.");
            test.log(Status.INFO, "Step 01: Click JS Confirm button and dismiss the dialog.");
            String result = jsAlertsPage.triggerAndDismissConfirm();

            log.info("Step 02: Verify the result text is 'You clicked: Cancel'.");
            test.log(Status.INFO, "Step 02: Verify the result text is 'You clicked: Cancel'.");
            Assert.assertEquals(result, "You clicked: Cancel",
                    "JS Confirm dismiss result text does not match.");

            test.log(Status.PASS, "JS Alerts Page – Test #03: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "JS Alerts Page – Test #03: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "JS Alerts Page – Test #03: FAILED – " + e.getMessage());
            throw e;
        }
    }

    @AfterMethod
    public void teardown() {
        driver.quit();
        log.info("--- [TEARDOWN] Browser closed ---");
    }

    @AfterSuite
    public void reportClosure() {
        if (extent != null) {
            extent.flush();
        }
    }
}
