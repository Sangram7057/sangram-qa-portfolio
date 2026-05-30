package com.sangram.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class TransactionsPage {
    private final WebDriver driver;

    private final By dateRange = By.cssSelector("[data-testid='date-range']");
    private final By applyFilters = By.cssSelector("[data-testid='apply-filters']");
    private final By table = By.cssSelector("[data-testid='transactions-table']");

    public TransactionsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/transactions");
    }

    public void filterLast30Days() {
        new Select(driver.findElement(dateRange)).selectByValue("last-30-days");
        driver.findElement(applyFilters).click();
    }

    public boolean tableVisible() {
        return driver.findElement(table).isDisplayed();
    }
}
