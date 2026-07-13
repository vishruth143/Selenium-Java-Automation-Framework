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
import com.herokuapp.pages.DynamicLoadingPage;
import com.util.TestUtil;

/**
 * Test Suite : Heroku – Dynamic Loading Page
 * URL        : https://the-internet.herokuapp.com/dynamic_loading/1
 *
 * Scenario   : Element is hidden on page; revealed after clicking Start.
 */
public class DynamicLoadingPageTest extends TestBase {

    Logger log;
    DynamicLoadingPage dynamicLoadingPage;

    public DynamicLoadingPageTest() {
        super();
        log = Logger.getLogger(DynamicLoadingPageTest.class);
    }

    @BeforeSuite
    public void reportSetup() {
        initializeReport();
    }

    @BeforeMethod
    public void setup() throws MalformedURLException {
        initialization();
        dynamicLoadingPage = new DynamicLoadingPage();
        dynamicLoadingPage.navigate();
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void dynamicLoadingPageHeadingTest() throws IOException {
        /*
         * Dynamic Loading Page – Test #01: Verify the page heading.
         * Steps:
         * 01) Navigate to the Dynamic Loading page.
         * 02) Verify the heading contains 'Dynamically Loaded Page Elements'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Dynamic Loading Page – Test #01: Verify the page heading.");
        test.assignCategory("Heroku – Dynamic Loading Page");

        log.info("*".repeat(80));
        log.info("Dynamic Loading Page – Test #01: Verify the page heading.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Verify the heading contains 'Dynamically Loaded Page Elements'.");
            test.log(Status.INFO, "Step 01: Verify the heading.");
            Assert.assertEquals(dynamicLoadingPage.getPageHeading(),
                    "Dynamically Loaded Page Elements",
                    "Dynamic Loading page heading does not match.");

            test.log(Status.PASS, "Dynamic Loading Page – Test #01: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dynamic Loading Page – Test #01: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dynamic Loading Page – Test #01: FAILED – " + e.getMessage());
            throw e;
        }
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void waitForDynamicElementTest() throws IOException {
        /*
         * Dynamic Loading Page – Test #02: Verify 'Hello World!' appears after clicking Start.
         * Steps:
         * 01) Click the Start button.
         * 02) Wait for the loading spinner to disappear.
         * 03) Verify the finish text is 'Hello World!'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Dynamic Loading Page – Test #02: Verify 'Hello World!' appears after clicking Start.");
        test.assignCategory("Heroku – Dynamic Loading Page");

        log.info("*".repeat(80));
        log.info("Dynamic Loading Page – Test #02: Wait for dynamic element.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Click the Start button.");
            test.log(Status.INFO, "Step 01: Click the Start button.");
            dynamicLoadingPage.clickStartButton();

            log.info("Step 02: Wait for loading to complete and verify finish text.");
            test.log(Status.INFO, "Step 02: Wait for loading to complete and verify finish text.");
            String finishText = dynamicLoadingPage.getFinishText(15);
            Assert.assertEquals(finishText, "Hello World!", "Finish text does not match.");

            test.log(Status.PASS, "Dynamic Loading Page – Test #02: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dynamic Loading Page – Test #02: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Dynamic Loading Page – Test #02: FAILED – " + e.getMessage());
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
