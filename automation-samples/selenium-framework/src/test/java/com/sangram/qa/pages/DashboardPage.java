package com.sangram.qa.pages;

import com.sangram.qa.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    private final WebDriver driver;
    private final WaitHelper waitHelper;

    private final By title = By.cssSelector("[data-testid='dashboard-title']");
    private final By accountSummary = By.cssSelector("[data-testid='account-summary-card']");
    private final By notificationsPanel = By.cssSelector("[data-testid='notifications-panel']");
    private final By profileMenu = By.cssSelector("[data-testid='profile-menu']");
    private final By signOut = By.cssSelector("[data-testid='sign-out']");

    public DashboardPage(WebDriver driver, WaitHelper waitHelper) {
        this.driver = driver;
        this.waitHelper = waitHelper;
    }

    public void waitUntilLoaded() {
        waitHelper.visible(title);
        waitHelper.visible(accountSummary);
    }

    public String titleText() {
        return waitHelper.visible(title).getText();
    }

    public boolean accountSummaryVisible() {
        return waitHelper.visible(accountSummary).isDisplayed();
    }

    public boolean notificationsPanelVisible() {
        return waitHelper.visible(notificationsPanel).isDisplayed();
    }

    public void signOut() {
        waitHelper.clickable(profileMenu).click();
        waitHelper.clickable(signOut).click();
    }
}
