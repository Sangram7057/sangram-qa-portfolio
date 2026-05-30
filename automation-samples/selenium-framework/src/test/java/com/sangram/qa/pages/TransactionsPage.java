package com.sangram.qa.pages;

import com.sangram.qa.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class TransactionsPage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    private final By dateRange = By.cssSelector("[data-testid='date-range']");
    private final By applyFilters = By.cssSelector("[data-testid='apply-filters']");
    private final By table = By.cssSelector("[data-testid='transactions-table']");

    public TransactionsPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/transactions");
    }

    public void filterLast30Days() {
        new Select(waitHelper.visible(dateRange)).selectByValue("last-30-days");
        waitHelper.clickable(applyFilters).click();
    }

    public boolean tableVisible() {
        return waitHelper.visible(table).isDisplayed();
    }
}
