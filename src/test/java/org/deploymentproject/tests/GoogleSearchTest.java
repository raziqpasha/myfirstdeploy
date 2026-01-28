package org.deploymentproject.tests;


import org.deploymentproject.base.BaseTest;
import org.deploymentproject.driver.DriverManager;
import org.deploymentproject.pages.GoogleHomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Sample test for Google Search
 */
public class GoogleSearchTest extends BaseTest {

    @Test
    public void testGooglePageTitle() {
        // Create page object
        GoogleHomePage googlePage = new GoogleHomePage(DriverManager.getDriver());

        // Get page title
        String title = googlePage.getPageTitle();

        // Verify title
        Assert.assertTrue(title.contains("Google"), "Page title should contain Google");

        System.out.println("Test Passed: Page title is " + title);
    }

    @Test
    public void testGoogleSearch() throws InterruptedException {
        // Create page object
        GoogleHomePage googlePage = new GoogleHomePage(DriverManager.getDriver());

        // Perform search
        googlePage.searchFor("Selenium WebDriver");

        // Wait for results to load
        Thread.sleep(2000);

        // Get new page title
        String title = googlePage.getPageTitle();

        // Verify search happened
        Assert.assertTrue(title.contains("Selenium WebDriver"),
                "Page title should contain search term");

        System.out.println("Test Passed: Search executed successfully");
    }
}