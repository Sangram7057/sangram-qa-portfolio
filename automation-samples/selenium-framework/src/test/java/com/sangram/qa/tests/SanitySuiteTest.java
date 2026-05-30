package com.sangram.qa.tests;

import com.sangram.qa.base.TestBase;
import com.sangram.qa.config.EnvConfig;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SanitySuiteTest extends TestBase {
    @Test(groups = {"sanity"})
    public void criticalAuthJourneyWorks() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        loginPage.login(EnvConfig.username(), EnvConfig.password());

        Assert.assertEquals(dashboardPage.title().getText(), "Dashboard");
        Assert.assertTrue(dashboardPage.accountSummary().isDisplayed());

        dashboardPage.signOut();
        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
    }
}
