package com.sangram.qa.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import com.sangram.qa.utilities.WaitHelper;

public class DashboardPage {
    private final WaitHelper waitHelper;

    private final By title = By.cssSelector("[data-testid='dashboard-title']");
    private final By accountSummary = By.cssSelector("[data-testid='account-summary-card']");
    private final By notificationsPanel = By.cssSelector("[data-testid='notifications-panel']");
    private final By profileMenu = By.cssSelector("[data-testid='profile-menu']");
    private final By signOut = By.cssSelector("[data-testid='sign-out']");

    public DashboardPage(WebDriver driver, WaitHelper waitHelper) {
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
