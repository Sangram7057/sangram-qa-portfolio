package com.sangram.qa.pages;

import com.sangram.qa.utilities.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AccountsPage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    private final By title = By.cssSelector("[data-testid='accounts-title']");
    private final By searchBox = By.cssSelector("[data-testid='account-search']");
    private final By grid = By.cssSelector("[data-testid='account-grid']");

    public AccountsPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
    }

    public void open(String baseUrl) {
        driver.get(baseUrl + "/accounts");
    }

    public String titleText() {
        return waitHelper.visible(title).getText();
    }

    public void search(String value) {
        waitHelper.visible(searchBox).clear();
        waitHelper.visible(searchBox).sendKeys(value);
    }

    public boolean gridVisible() {
        return waitHelper.visible(grid).isDisplayed();
    }
}
