package com.sangram.qa.tests;

import com.sangram.qa.base.TestBase;
import com.sangram.qa.config.EnvConfig;
import com.sangram.qa.data.TestUsers;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeSuiteTest extends TestBase {
    @Test(groups = {"smoke"})
    public void validUserCanLoginAndViewDashboardWidgets() {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);

        loginPage.open(EnvConfig.baseUrl());
        loginPage.login(TestUsers.validUser().username(), TestUsers.validUser().password());
        waitHelper.urlContains("/dashboard");
        dashboardPage.waitUntilLoaded();

        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
        Assert.assertTrue(
            dashboardPage.notificationsPanelVisible(),
            "Notifications panel should be visible after login."
        );
    }

    @Test(groups = {"smoke"})
    public void userCanSignOutCleanlyFromHeaderMenu() {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);

        loginPage.open(EnvConfig.baseUrl());
        loginPage.login(TestUsers.validUser().username(), TestUsers.validUser().password());
        waitHelper.urlContains("/dashboard");
        dashboardPage.waitUntilLoaded();
        dashboardPage.signOut();
        waitHelper.urlContains("/login");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
        Assert.assertTrue(loginPage.signInVisible(), "Sign in button should be visible after sign out.");
    }
}
