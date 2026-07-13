package com.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

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

    public ApiTestBase() {
        log = Logger.getLogger(ApiTestBase.class);

        activeApp = System.getProperty("app", "jsonplaceholder");
        String envValue = System.getProperty("env");

        try {
            activeEnvironment = Environment.fromString(envValue);
            String configFileName = "config." + activeEnvironment.getKey() + ".properties";
            String configFilePath = System.getProperty("user.dir")
                    + "/src/main/java/com/" + activeApp + "/config/" + configFileName;

            log.info("Active application  : " + activeApp);
            log.info("Active environment  : " + activeEnvironment.name());
            log.info("Loading config file : " + configFilePath);

            prop = new Properties();
            prop.load(new FileInputStream(configFilePath));

            RestAssured.baseURI = prop.getProperty("baseUri");
            log.info("REST Assured baseURI: " + RestAssured.baseURI);

        } catch (FileNotFoundException e) {
            log.error("Config file not found for app=" + activeApp + " env=" + envValue, e);
        } catch (IOException e) {
            log.error("Failed to load config for app=" + activeApp + " env=" + envValue, e);
        }
    }
}
