package com.sangram.qa.tests;

import com.sangram.qa.base.TestBase;
import com.sangram.qa.config.EnvConfig;
import com.sangram.qa.pages.AccountsPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.pages.TransactionsPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegressionSuiteTest extends TestBase {
    @Test(groups = {"regression"})
    public void accountsAndTransactionsFlowsWork() {
        LoginPage loginPage = new LoginPage(driver);
        AccountsPage accountsPage = new AccountsPage(driver);
        TransactionsPage transactionsPage = new TransactionsPage(driver);

        loginPage.login(EnvConfig.username(), EnvConfig.password());

        accountsPage.open(EnvConfig.baseUrl());
        Assert.assertEquals(accountsPage.titleText(), "Accounts");
        accountsPage.search("primary");
        Assert.assertTrue(accountsPage.gridVisible());

        transactionsPage.open(EnvConfig.baseUrl());
        transactionsPage.filterLast30Days();
        Assert.assertTrue(transactionsPage.tableVisible());
    }
}
