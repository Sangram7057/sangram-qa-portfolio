package com.sangram.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DashboardPage {
    private final WebDriver driver;

    private final By title = By.cssSelector("[data-testid='dashboard-title']");
    private final By accountSummary = By.cssSelector("[data-testid='account-summary-card']");
    private final By profileMenu = By.cssSelector("[data-testid='profile-menu']");
    private final By signOut = By.cssSelector("[data-testid='sign-out']");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement title() {
        return driver.findElement(title);
    }

    public WebElement accountSummary() {
        return driver.findElement(accountSummary);
    }

    public void signOut() {
        driver.findElement(profileMenu).click();
        driver.findElement(signOut).click();
    }
}
