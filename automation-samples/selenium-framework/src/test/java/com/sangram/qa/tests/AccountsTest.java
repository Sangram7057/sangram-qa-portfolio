package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.AccountSearchScenario;
import com.sangram.qa.pages.AccountsPage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountsTest extends AuthenticatedTestBase {
    @Test(
        groups = {"sanity"},
        dataProvider = "accountSearchCsvData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate that account search keeps the results grid visible for critical scenarios."
    )
    public void accountSearchKeepsGridVisible(AccountSearchScenario scenario) {
        loginWithDefaultUser();
        AccountsPage accountsPage = new AccountsPage(driver, waitHelper);

        accountsPage.open(ConfigReader.baseUrl());
        accountsPage.search(scenario.searchTerm());

        Assert.assertEquals(accountsPage.titleText(), scenario.expectedAccountsTitle());
        Assert.assertTrue(accountsPage.gridVisible(), "Account grid should remain visible after search.");
    }

    @Test(
        groups = {"regression"},
        dataProvider = "accountSearchCsvData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate account module title and search results across regression scenarios."
    )
    public void accountModuleSupportsScenarioDrivenSearches(AccountSearchScenario scenario) {
        loginWithDefaultUser();
        AccountsPage accountsPage = new AccountsPage(driver, waitHelper);

        accountsPage.open(ConfigReader.baseUrl());
        Assert.assertEquals(accountsPage.titleText(), scenario.expectedAccountsTitle());

        accountsPage.search(scenario.searchTerm());
        Assert.assertTrue(accountsPage.gridVisible(), "Account grid should remain visible for regression searches.");
    }
}
