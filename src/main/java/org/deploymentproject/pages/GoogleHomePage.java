package org.deploymentproject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object for Google Home Page
 */
public class GoogleHomePage {

    private WebDriver driver;

    // Locate search box element
    @FindBy(name = "q")
    private WebElement searchBox;

    // Constructor
    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Type search query and submit
     */
    public void searchFor(String searchText) {
        searchBox.sendKeys(searchText);
        searchBox.submit();
    }

    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}