package org.deploymentproject.base;

import org.deploymentproject.driver.DriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Base class for all test classes
 * Handles setup and teardown
 */
public class BaseTest {

    protected Properties config;

    @BeforeMethod
    public void setUp() {
        try {
            // Load configuration from data.properties
            config = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/data.properties");
            config.load(fis);

            // Get Selenium Hub URL from environment variable
            String hubUrl = System.getenv("SELENIUM_HUB_URL");

            // If environment variable not set, use default
            if (hubUrl == null || hubUrl.isEmpty()) {
                hubUrl = "http://localhost:4444/wd/hub";
            }

            System.out.println("Connecting to Selenium Hub: " + hubUrl);

            // Create WebDriver
            DriverManager.createDriver(hubUrl);

            // Navigate to base URL
            String baseUrl = config.getProperty("base.url");
            DriverManager.getDriver().get(baseUrl);

        } catch (Exception e) {
            throw new RuntimeException("Setup failed: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        // Close browser and quit driver
        DriverManager.quitDriver();
    }
}