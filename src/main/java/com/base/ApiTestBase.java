package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;

/**
 * Shared base class for all API test suites.
 *
 * Resolves the active application and environment at runtime from
 * JVM system properties, loads the corresponding config file, and
 * configures REST Assured's base URI automatically.
 *
 * Usage:
 *   All API test classes must extend this class.
 *   System properties:
 *     -Dapp=jsonplaceholder     (application name, default: jsonplaceholder)
 *     -Denv=qa                  (environment, default: qa)
 *
 * Config file resolved as:
 *   src/main/java/com/<app>/config/config.<env>.properties
 */
public class ApiTestBase {

    public static Properties prop;
    public static Logger log;
    public static Environment activeEnvironment;
    public static String activeApp;

    private static volatile boolean configLogged = false;

    public ApiTestBase() {
        log = Logger.getLogger(ApiTestBase.class);

        activeApp = System.getProperty("app", "jsonplaceholder");
        String envValue = System.getProperty("env");

        try {
            activeEnvironment = Environment.fromString(envValue);
            String configFileName = "config." + activeEnvironment.getKey() + ".properties";
            String configFilePath = System.getProperty("user.dir")
                    + "/src/main/java/com/" + activeApp + "/config/" + configFileName;

            prop = new Properties();
            prop.load(new FileInputStream(configFilePath));
            RestAssured.baseURI = prop.getProperty("baseUri");

            // Log config details only once across all test class instantiations
            if (!configLogged) {
                configLogged = true;
                log.info("=".repeat(100));
                log.info("  Active application  : " + activeApp);
                log.info("  Active environment  : " + activeEnvironment.name());
                log.info("  Loading config file : " + configFilePath);
                log.info("  REST Assured baseURI: " + RestAssured.baseURI);
                log.info("=".repeat(100));
            }

        } catch (FileNotFoundException e) {
            log.error("Config file not found for app=" + activeApp + " env=" + envValue, e);
        } catch (IOException e) {
            log.error("Failed to load config for app=" + activeApp + " env=" + envValue, e);
        }
    }

    @BeforeClass(alwaysRun = true)
    public void logApiTestClassStart() {
        Logger classLog = Logger.getLogger(this.getClass());
        classLog.info("");
        classLog.info(">>>>>>>>>> [TEST CLASS START] " + this.getClass().getSimpleName() + " <<<<<<<<<<");
    }

    @AfterClass(alwaysRun = true)
    public void logApiTestClassEnd() {
        Logger classLog = Logger.getLogger(this.getClass());
        classLog.info("<<<<<<<<<< [TEST CLASS END]   " + this.getClass().getSimpleName() + " >>>>>>>>>>");
        classLog.info("");
    }
}