package com.sangram.qa.tests;

import com.sangram.qa.base.AuthenticatedTestBase;
import com.sangram.qa.models.AccountSearchScenario;
import com.sangram.qa.pages.ProfilePage;
import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.TestDataProviders;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ProfileTest extends AuthenticatedTestBase {
    @Test(
        groups = {"regression"},
        dataProvider = "accountSearchCsvData",
        dataProviderClass = TestDataProviders.class,
        description = "Validate the profile page title after authenticated module navigation."
    )
    public void profilePageLoadsWithExpectedTitle(AccountSearchScenario scenario) {
        loginWithDefaultUser();
        ProfilePage profilePage = new ProfilePage(driver, waitHelper);

        profilePage.open(ConfigReader.baseUrl());
        Assert.assertEquals(profilePage.titleText(), scenario.expectedProfileTitle());
    }
}
