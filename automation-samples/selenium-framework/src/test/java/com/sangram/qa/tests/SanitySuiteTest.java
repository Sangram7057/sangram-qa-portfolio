package com.sangram.qa.tests;

import com.sangram.qa.base.BaseTest;
import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SanitySuiteTest extends BaseTest {
    @Test(
        groups = {"sanity"},
        dataProvider = "loginJsonData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate the critical authentication journey using JSON-driven scenarios."
    )
    public void criticalAuthJourneyWorks(LoginScenario scenario) {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);

        logger.info("Running sanity scenario {}", scenario.scenarioName());
        loginPage.open(ConfigReader.baseUrl());
        loginPage.login(scenario.username(), scenario.password());
        waitHelper.urlContains(scenario.expectedUrlFragment());
        dashboardPage.waitUntilLoaded();

        Assert.assertEquals(dashboardPage.titleText(), scenario.dashboardTitle());
        Assert.assertTrue(dashboardPage.accountSummaryVisible());

        dashboardPage.signOut();
        waitHelper.urlContains("/login");
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
        Assert.assertTrue(loginPage.signInVisible(), "Sign in button should be visible after sign out.");
    }
}
