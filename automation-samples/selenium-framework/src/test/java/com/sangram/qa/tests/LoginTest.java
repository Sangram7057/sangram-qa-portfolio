package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends AuthenticatedTestBase {
    @Test(
        groups = {"smoke"},
        dataProvider = "loginExcelData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate that a valid user can sign in and land on the dashboard."
    )
    public void validUserCanLoginToDashboard(LoginScenario scenario) {
        DashboardPage dashboardPage = loginToDashboard(scenario);

        Assert.assertTrue(driver.getCurrentUrl().contains(scenario.expectedUrlFragment()));
        Assert.assertEquals(dashboardPage.titleText(), scenario.dashboardTitle());
    }
}
