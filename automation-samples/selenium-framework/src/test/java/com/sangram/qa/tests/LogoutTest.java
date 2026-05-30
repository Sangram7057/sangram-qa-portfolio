package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LogoutTest extends AuthenticatedTestBase {
    @Test(
        groups = {"smoke"},
        dataProvider = "loginExcelData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate a user can sign out from the header menu."
    )
    public void userCanSignOutFromHeaderMenu(LoginScenario scenario) {
        DashboardPage dashboardPage = loginToDashboard(scenario);
        LoginPage loginPage = loginPage();

        dashboardPage.signOut();
        waitHelper.urlContains("/login");

        Assert.assertTrue(driver.getCurrentUrl().contains("/login"));
        Assert.assertTrue(loginPage.signInVisible(), "Sign in button should be visible after sign out.");
    }
}
