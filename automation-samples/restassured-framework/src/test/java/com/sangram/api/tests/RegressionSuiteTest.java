package com.sangram.api.tests;

import com.sangram.api.base.ApiTestBase;
import com.sangram.api.data.ApiTestData;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegressionSuiteTest extends ApiTestBase {
    @Test(groups = {"regression"})
    public void customerProfileReturnsMandatoryContactFields() {
        customersClient.getCustomerProfile(ApiTestData.PRIMARY_CUSTOMER_ID)
            .then()
            .statusCode(200)
            .body("status", equalTo("SUCCESS"))
            .body("data.customerId", equalTo(ApiTestData.PRIMARY_CUSTOMER_ID))
            .body("data.profile.email", notNullValue())
            .body("data.profile.phone", notNullValue())
            .body("data.preferences.language", notNullValue());
    }

    @Test(groups = {"regression"})
    public void accountSummarySupportsMultipleAccountsInContract() {
        accountsClient.getAccountSummary()
            .then()
            .statusCode(200)
            .body("data.accounts.size()", greaterThanOrEqualTo(1))
            .body("data.accounts[0].accountId", notNullValue())
            .body("data.accounts[0].accountType", notNullValue())
            .body("data.accounts[0].currency", notNullValue());
    }

    @Test(groups = {"regression"})
    public void invalidAccountIdReturnsNotFoundContract() {
        accountsClient.getAccountDetails(ApiTestData.INVALID_ACCOUNT_ID)
            .then()
            .statusCode(404)
            .body("status", equalTo("NOT_FOUND"))
            .body("error.code", equalTo("ACCOUNT_404"))
            .body("error.message", notNullValue());
    }

    @Test(groups = {"regression"})
    public void unauthorizedAccountSummaryRequestIsRejected() {
        accountsClient.getAccountSummaryWithoutAuth()
            .then()
            .statusCode(401)
            .body("status", equalTo("UNAUTHORIZED"))
            .body("error.code", equalTo("AUTH_401"))
            .body("error.message", notNullValue());
    }
}
