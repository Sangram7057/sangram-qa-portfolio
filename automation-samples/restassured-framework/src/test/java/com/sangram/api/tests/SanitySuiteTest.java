package com.sangram.api.tests;

import com.sangram.api.base.ApiTestBase;
import com.sangram.api.data.ApiTestData;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.oneOf;

public class SanitySuiteTest extends ApiTestBase {
    @Test(groups = {"sanity"})
    public void accountDetailsReturnsOwnerAndBalanceFields() {
        accountsClient.getAccountDetails(ApiTestData.PRIMARY_ACCOUNT_ID)
            .then()
            .statusCode(200)
            .body("status", equalTo("SUCCESS"))
            .body("data.accountId", equalTo(ApiTestData.PRIMARY_ACCOUNT_ID))
            .body("data.owner.customerId", notNullValue())
            .body("data.currency", notNullValue())
            .body("data.availableBalance", notNullValue());
    }

    @Test(groups = {"sanity"})
    public void transactionHistorySupportsDateRangeFiltering() {
        accountsClient.getTransactions(ApiTestData.PRIMARY_ACCOUNT_ID, ApiTestData.LAST_30_DAYS, ApiTestData.CREDIT)
            .then()
            .statusCode(200)
            .body("status", equalTo("SUCCESS"))
            .body("data.filters.dateRange", equalTo(ApiTestData.LAST_30_DAYS))
            .body("data.transactions.size()", greaterThanOrEqualTo(1))
            .body("data.transactions[0].transactionId", notNullValue())
            .body("data.transactions[0].type", oneOf("credit", "debit"))
            .body("data.transactions[0].amount", notNullValue());
    }
}
