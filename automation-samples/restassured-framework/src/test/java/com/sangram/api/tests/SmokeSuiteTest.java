package com.sangram.api.tests;

import com.sangram.api.base.ApiTestBase;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

public class SmokeSuiteTest extends ApiTestBase {
    @Test(groups = {"smoke"})
    public void accountSummaryReturnsExpectedContract() {
        accountsClient.getAccountSummary()
            .then()
            .statusCode(200)
            .body("status", equalTo("SUCCESS"))
            .body("data.customerId", notNullValue())
            .body("data.accounts.size()", greaterThanOrEqualTo(1))
            .body("data.accounts[0].accountId", notNullValue())
            .body("data.accounts[0].currency", notNullValue())
            .body("data.accounts[0].availableBalance", notNullValue());
    }

    @Test(groups = {"smoke"})
    public void accountSummaryIncludesPrimaryBalanceWidgets() {
        accountsClient.getAccountSummary()
            .then()
            .statusCode(200)
            .body("data.accounts[0].ledgerBalance", notNullValue())
            .body("data.accounts[0].availableBalance", notNullValue())
            .body("data.accounts[0].accountType", notNullValue());
    }
}
