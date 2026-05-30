package com.sangram.qa.tests;

import com.sangram.qa.base.BaseTest;
import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SmokeSuiteTest extends BaseTest {
    @Test(
        groups = {"smoke"},
        dataProvider = "loginExcelData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate login and dashboard widgets using Excel-driven credentials."
    )
    public void validUserCanLoginAndViewDashboardWidgets(LoginScenario scenario) {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);

        logger.info("Running smoke scenario {}", scenario.scenarioName());
        loginPage.open(ConfigReader.baseUrl());
        loginPage.login(scenario.username(), scenario.password());
        waitHelper.urlContains(scenario.expectedUrlFragment());
        dashboardPage.waitUntilLoaded();

        Assert.assertTrue(driver.getCurrentUrl().contains(scenario.expectedUrlFragment()));
        Assert.assertEquals(dashboardPage.titleText(), scenario.dashboardTitle());
        Assert.assertTrue(
            dashboardPage.notificationsPanelVisible(),
            "Notifications panel should be visible after login."
        );
    }

    @Test(
        groups = {"smoke"},
        dataProvider = "loginExcelData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate sign out using Excel-driven credentials."
    )
    public void userCanSignOutCleanlyFromHeaderMenu(LoginScenario scenario) {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);

        logger.info("Running sign-out smoke scenario {}", scenario.scenarioName());
        loginPage.open(ConfigReader.baseUrl());
        loginPage.login(scenario.username(), scenario.password());
        waitHelper.urlContains(scenario.expectedUrlFragment());
        dashboardPage.waitUntilLoaded();
        dashboardPage.signOut();
        waitHelper.urlContains("/login");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
        Assert.assertTrue(loginPage.signInVisible(), "Sign in button should be visible after sign out.");
    }
}
