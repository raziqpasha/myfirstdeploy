package org.deploymentproject.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.net.URL;

/**
 * Manages WebDriver creation and lifecycle
 */
public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    /**
     * Creates a RemoteWebDriver connected to Selenium Grid
     * @param hubUrl - Selenium Hub URL (from environment variable)
     */
    public static void createDriver(String hubUrl) {
        try {
            ChromeOptions options = new ChromeOptions();

            // Add arguments for running Chrome in Docker/headless environments
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-blink-features=AutomationControlled");
            options.addArguments("--remote-allow-origins=*");

            // Create RemoteWebDriver pointing to Selenium Grid
            WebDriver remoteDriver = new RemoteWebDriver(new URL(hubUrl), options);
            driver.set(remoteDriver);

            // Maximize browser window
            driver.get().manage().window().maximize();

        } catch (Exception e) {
            throw new RuntimeException("Failed to create driver: " + e.getMessage());
        }
    }

    /**
     * Returns the current WebDriver instance
     */
    public static WebDriver getDriver() {
        return driver.get();
    }

    /**
     * Closes browser and quits driver
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}