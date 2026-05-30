package com.sangram.qa.tests;

import com.sangram.qa.base.BaseTest;
import com.sangram.qa.models.AccountSearchScenario;
import com.sangram.qa.pages.AccountsPage;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.pages.ProfilePage;
import com.sangram.qa.pages.TransactionsPage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegressionSuiteTest extends BaseTest {
    @Test(
        groups = {"regression"},
        dataProvider = "accountSearchCsvData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate accounts, transaction filters, and profile checks using CSV-driven data."
    )
    public void accountsAndTransactionsFlowsWork(AccountSearchScenario scenario) {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);
        AccountsPage accountsPage = new AccountsPage(driver, waitHelper);
        TransactionsPage transactionsPage = new TransactionsPage(driver, waitHelper);
        ProfilePage profilePage = new ProfilePage(driver, waitHelper);

        logger.info("Running regression scenario {}", scenario.scenarioName());
        loginPage.open(ConfigReader.baseUrl());
        loginPage.login(ConfigReader.defaultUsername(), ConfigReader.defaultPassword());
        waitHelper.urlContains("/dashboard");
        dashboardPage.waitUntilLoaded();
        Assert.assertTrue(dashboardPage.accountSummaryVisible());

        accountsPage.open(ConfigReader.baseUrl());
        Assert.assertEquals(accountsPage.titleText(), scenario.expectedAccountsTitle());
        accountsPage.search(scenario.searchTerm());
        Assert.assertTrue(accountsPage.gridVisible(), "Account grid should stay visible after account search.");

        transactionsPage.open(ConfigReader.baseUrl());
        transactionsPage.filterByDateRange(scenario.dateRange());
        Assert.assertTrue(transactionsPage.tableVisible(), "Transactions table should be visible after filtering.");

        profilePage.open(ConfigReader.baseUrl());
        Assert.assertEquals(profilePage.titleText(), scenario.expectedProfileTitle());
    }
}
