package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.AccountSearchScenario;
import com.sangram.qa.pages.TransactionsPage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TransactionsRegressionTest extends AuthenticatedTestBase {
    @Test(
        groups = {"regression"},
        dataProvider = "accountSearchCsvData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate transaction table visibility across scenario-driven date ranges."
    )
    public void transactionModuleSupportsScenarioDateRanges(AccountSearchScenario scenario) {
        loginWithDefaultUser();
        TransactionsPage transactionsPage = new TransactionsPage(driver, waitHelper);

        transactionsPage.open(ConfigReader.baseUrl());
        transactionsPage.filterByDateRange(scenario.dateRange());

        Assert.assertTrue(transactionsPage.tableVisible(), "Transactions table should be visible for regression filters.");
    }
}
