package com.sangram.qa.models;

public record LoginScenario(
    String scenarioName,
    String username,
    String password,
    String expectedUrlFragment,
    String dashboardTitle
) {
}
