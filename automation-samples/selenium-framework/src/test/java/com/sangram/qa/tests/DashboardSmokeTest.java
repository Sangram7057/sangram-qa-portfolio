package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DashboardSmokeTest extends AuthenticatedTestBase {
    @Test(
        groups = {"smoke"},
        dataProvider = "loginExcelData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate dashboard summary widgets are visible after login."
    )
    public void dashboardWidgetsAreVisibleAfterLogin(LoginScenario scenario) {
        DashboardPage dashboardPage = loginToDashboard(scenario);

        Assert.assertTrue(dashboardPage.accountSummaryVisible(), "Account summary card should be visible.");
        Assert.assertTrue(dashboardPage.notificationsPanelVisible(), "Notifications panel should be visible.");
    }
}
