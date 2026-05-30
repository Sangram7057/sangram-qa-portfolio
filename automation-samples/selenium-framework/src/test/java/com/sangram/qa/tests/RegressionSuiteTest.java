package com.sangram.qa.tests;

import com.sangram.qa.base.TestBase;
import com.sangram.qa.config.EnvConfig;
import com.sangram.qa.data.TestUsers;
import com.sangram.qa.pages.AccountsPage;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.pages.ProfilePage;
import com.sangram.qa.pages.TransactionsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegressionSuiteTest extends TestBase {
    @Test(groups = {"regression"})
    public void accountsAndTransactionsFlowsWork() {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);
        AccountsPage accountsPage = new AccountsPage(driver, waitHelper);
        TransactionsPage transactionsPage = new TransactionsPage(driver, waitHelper);
        ProfilePage profilePage = new ProfilePage(driver, waitHelper);

        loginPage.open(EnvConfig.baseUrl());
        loginPage.login(TestUsers.validUser().username(), TestUsers.validUser().password());
        waitHelper.urlContains("/dashboard");
        dashboardPage.waitUntilLoaded();
        Assert.assertTrue(dashboardPage.accountSummaryVisible());

        accountsPage.open(EnvConfig.baseUrl());
        Assert.assertEquals(accountsPage.titleText(), "Accounts");
        accountsPage.search("primary");
        Assert.assertTrue(accountsPage.gridVisible(), "Account grid should stay visible after account search.");

        transactionsPage.open(EnvConfig.baseUrl());
        transactionsPage.filterLast30Days();
        Assert.assertTrue(transactionsPage.tableVisible(), "Transactions table should be visible after filtering.");

        profilePage.open(EnvConfig.baseUrl());
        Assert.assertEquals(profilePage.titleText(), "Profile");
    }
}
