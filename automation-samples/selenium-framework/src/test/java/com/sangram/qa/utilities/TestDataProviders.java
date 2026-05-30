package com.sangram.qa.utilities;

import com.sangram.qa.models.AccountSearchScenario;
import com.sangram.qa.models.LoginScenario;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

public final class TestDataProviders {
    private TestDataProviders() {
    }

    @DataProvider(name = "loginExcelData")
    public static Object[][] loginExcelData() {
        List<Map<String, String>> rows = ExcelUtils.readSheet("testdata.xlsx", "LoginData");
        return rows.stream()
            .map(TestDataProviders::toLoginScenario)
            .map(scenario -> new Object[] {scenario})
            .toArray(Object[][]::new);
    }

    @DataProvider(name = "loginJsonData")
    public static Object[][] loginJsonData() {
        return JsonUtils.readList("testdata/login-data.json", LoginScenario[].class).stream()
            .map(scenario -> new Object[] {scenario})
            .toArray(Object[][]::new);
    }

    @DataProvider(name = "accountSearchCsvData")
    public static Object[][] accountSearchCsvData() {
        List<Map<String, String>> rows = CsvUtils.readRows("testdata/account-search-data.csv");
        return rows.stream()
            .map(TestDataProviders::toAccountScenario)
            .map(scenario -> new Object[] {scenario})
            .toArray(Object[][]::new);
    }

    private static LoginScenario toLoginScenario(Map<String, String> row) {
        return new LoginScenario(
            row.getOrDefault("scenarioName", "Login Scenario"),
            row.getOrDefault("username", ConfigReader.defaultUsername()),
            row.getOrDefault("password", ConfigReader.defaultPassword()),
            row.getOrDefault("expectedUrlFragment", "/dashboard"),
            row.getOrDefault("dashboardTitle", "Dashboard")
        );
    }

    private static AccountSearchScenario toAccountScenario(Map<String, String> row) {
        return new AccountSearchScenario(
            row.getOrDefault("scenarioName", "Account Search Scenario"),
            row.getOrDefault("searchTerm", "primary"),
            row.getOrDefault("dateRange", "last-30-days"),
            row.getOrDefault("expectedAccountsTitle", "Accounts"),
            row.getOrDefault("expectedProfileTitle", "Profile")
        );
    }
}
