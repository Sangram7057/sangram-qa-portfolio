package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AuthenticationSanityTest extends AuthenticatedTestBase {
    @Test(
        groups = {"sanity"},
        dataProvider = "loginJsonData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate the critical authentication journey from sign in through sign out."
    )
    public void criticalAuthenticationJourneyWorks(LoginScenario scenario) {
        DashboardPage dashboardPage = loginToDashboard(scenario);
        LoginPage loginPage = loginPage();

        Assert.assertEquals(dashboardPage.titleText(), scenario.dashboardTitle());
        Assert.assertTrue(dashboardPage.accountSummaryVisible(), "Account summary should stay visible on dashboard.");

        dashboardPage.signOut();
        waitHelper.urlContains("/login");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
        Assert.assertTrue(loginPage.signInVisible(), "Sign in button should be visible after sign out.");
    }
}
