package com.sangram.qa.base;

import com.sangram.qa.models.LoginScenario;
import com.sangram.qa.pages.DashboardPage;
import com.sangram.qa.pages.LoginPage;
import com.sangram.qa.utilities.ConfigReader;

public abstract class AuthenticatedTestBase extends BaseTest {
    protected DashboardPage loginToDashboard(LoginScenario scenario) {
        LoginPage loginPage = new LoginPage(driver, waitHelper);
        DashboardPage dashboardPage = new DashboardPage(driver, waitHelper);

        logger.info("Logging in with scenario {}", scenario.scenarioName());
        loginPage.open(ConfigReader.baseUrl());
        loginPage.login(scenario.username(), scenario.password());
        waitHelper.urlContains(scenario.expectedUrlFragment());
        dashboardPage.waitUntilLoaded();

        return dashboardPage;
    }

    protected DashboardPage loginWithDefaultUser() {
        return loginToDashboard(
            new LoginScenario(
                "Default framework user",
                ConfigReader.defaultUsername(),
                ConfigReader.defaultPassword(),
                "/dashboard",
                "Dashboard"
            )
        );
    }

    protected LoginPage loginPage() {
        return new LoginPage(driver, waitHelper);
    }
}
