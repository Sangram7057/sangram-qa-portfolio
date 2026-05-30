package com.sangram.qa.models;

public record AccountSearchScenario(
    String scenarioName,
    String searchTerm,
    String dateRange,
    String expectedAccountsTitle,
    String expectedProfileTitle
) {
}
