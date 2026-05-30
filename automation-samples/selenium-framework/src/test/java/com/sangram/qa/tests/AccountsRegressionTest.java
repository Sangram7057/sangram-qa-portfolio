package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.AccountSearchScenario;
import com.sangram.qa.pages.AccountsPage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AccountsRegressionTest extends AuthenticatedTestBase {
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
