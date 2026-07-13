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
import com.herokuapp.pages.LoginPage;
import com.herokuapp.pages.SecureAreaPage;
import com.util.TestUtil;

/**
 * Test Suite : Heroku – Login Page
 * URL        : https://the-internet.herokuapp.com/login
 */
public class LoginPageTest extends TestBase {

    Logger log;
    LoginPage loginPage;
    SecureAreaPage secureAreaPage;

    public LoginPageTest() {
        super();
        log = Logger.getLogger(LoginPageTest.class);
    }

    @BeforeSuite
    public void reportSetup() {
        initializeReport();
    }

    @BeforeMethod
    public void setup() throws MalformedURLException {
        initialization();
        loginPage = new LoginPage();
        loginPage.navigate();
    }

    // ── Test #01 ──────────────────────────────────────────────────────────────
    @Test(priority = 1)
    public void loginPageHeadingTest() throws IOException {
        /*
         * Login Page – Test #01: Verify the 'Login Page' heading.
         * Steps:
         * 01) Navigate to the Heroku login page.
         * 02) Verify the page heading is 'Login Page'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Login Page – Test #01: Verify the 'Login Page' heading.");
        test.assignCategory("Heroku – Login Page");

        log.info("*".repeat(80));
        log.info("Login Page – Test #01: Verify the 'Login Page' heading.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Navigate to the Heroku login page.");
            test.log(Status.INFO, "Step 01: Navigate to the Heroku login page.");

            log.info("Step 02: Verify the page heading is 'Login Page'.");
            test.log(Status.INFO, "Step 02: Verify the page heading is 'Login Page'.");
            Assert.assertEquals(loginPage.getPageHeading(), "Login Page",
                    "Login page heading does not match.");

            test.log(Status.PASS, "Login Page – Test #01: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Login Page – Test #01: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Login Page – Test #01: FAILED – " + e.getMessage());
            throw e;
        }
    }

    // ── Test #02 ──────────────────────────────────────────────────────────────
    @Test(priority = 2)
    public void validLoginTest() throws IOException {
        /*
         * Login Page – Test #02: Verify successful login with valid credentials.
         * Steps:
         * 01) Enter valid username and password.
         * 02) Click the login button.
         * 03) Verify the success flash message contains 'You logged into a secure area!'.
         */
        String className  = new Exception().getStackTrace()[0].getClassName();
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(className + " : " + methodName,
                "Login Page – Test #02: Verify successful login with valid credentials.");
        test.assignCategory("Heroku – Login Page");

        log.info("*".repeat(80));
        log.info("Login Page – Test #02: Verify successful login.");
        log.info("*".repeat(80));

        try {
            log.info("Step 01: Enter valid username and password and click login.");
            test.log(Status.INFO, "Step 01: Enter valid username and password and click login.");
            secureAreaPage = loginPage.login(
                    prop.getProperty("username"),
                    prop.getProperty("password"));

            log.info("Step 02: Verify the success flash message.");
            test.log(Status.INFO, "Step 02: Verify the success flash message.");
            Assert.assertTrue(secureAreaPage.getFlashMessage()
                    .contains("You logged into a secure area!"),
                    "Success message not found after login.");

            test.log(Status.PASS, "Login Page – Test #02: PASSED");
        } catch (AssertionError ae) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Login Page – Test #02: FAILED – " + ae.getMessage());
            throw ae;
        } catch (Exception e) {
            TestUtil.takeScreenShot(className + "_" + methodName);
            test.log(Status.FAIL, "Login Page – Test #02: FAILED – " + e.getMessage());
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
