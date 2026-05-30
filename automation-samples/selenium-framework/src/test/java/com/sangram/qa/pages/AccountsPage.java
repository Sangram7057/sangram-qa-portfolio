package com.sangram.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsPage {
    private final WebDriver driver;

    private final By title = By.cssSelector("[data-testid='accounts-title']");
    private final By searchBox = By.cssSelector("[data-testid='account-search']");
    private final By grid = By.cssSelector("[data-testid='account-grid']");

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/accounts");
    }

    public String titleText() {
        return driver.findElement(title).getText();
    }

    public void search(String value) {
        driver.findElement(searchBox).sendKeys(value);
    }

    public boolean gridVisible() {
        return driver.findElement(grid).isDisplayed();
    }
}
