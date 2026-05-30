package com.sangram.api.tests;

import com.sangram.api.assertions.AccountAssertions;
import com.sangram.api.assertions.CustomerAssertions;
import com.sangram.api.assertions.ResponseAssertions;
import com.sangram.api.base.ApiTestBase;
import com.sangram.api.builders.CustomerPreferencesRequestBuilder;
import com.sangram.api.data.ApiTestData;
import com.sangram.api.models.request.CustomerPreferencesRequest;
import com.sangram.api.models.response.AccountSummaryResponse;
import com.sangram.api.models.response.ApiErrorResponse;
import com.sangram.api.models.response.CustomerPreferencesResponse;
import com.sangram.api.models.response.CustomerProfileResponse;
import com.sangram.api.utils.JsonUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RegressionSuiteTest extends ApiTestBase {
    @Test(groups = {"regression"})
    public void customerProfileReturnsMandatoryContactFields() {
        Response response = customersClient.getCustomerProfile(ApiTestData.PRIMARY_CUSTOMER_ID);
        ResponseAssertions.assertStatusCode(response, 200);

        CustomerProfileResponse customerProfileResponse = JsonUtils.fromResponse(response, CustomerProfileResponse.class);
        CustomerAssertions.assertCustomerProfile(customerProfileResponse, ApiTestData.PRIMARY_CUSTOMER_ID);
    }

    @Test(groups = {"regression"})
    public void accountSummarySupportsMultipleAccountsInContract() {
        Response response = accountsClient.getAccountSummary();
        ResponseAssertions.assertStatusCode(response, 200);

        AccountSummaryResponse summaryResponse = JsonUtils.fromResponse(response, AccountSummaryResponse.class);
        AccountAssertions.assertSummaryContract(summaryResponse);
        Assert.assertTrue(summaryResponse.getData().getAccounts().size() >= 1, "Expected at least one account.");
    }

    @Test(groups = {"regression"})
    public void customerPreferencesUpdateAcceptsSerializedPojoRequest() {
        CustomerPreferencesRequest request = new CustomerPreferencesRequestBuilder()
            .language(ApiTestData.DEFAULT_LANGUAGE)
            .emailAlerts(true)
            .smsAlerts(true)
            .pushAlerts(false)
            .build();

        Response response = customersClient.updateCustomerPreferences(ApiTestData.PRIMARY_CUSTOMER_ID, request);
        ResponseAssertions.assertStatusCode(response, 200);

        CustomerPreferencesResponse preferencesResponse = JsonUtils.fromResponse(response, CustomerPreferencesResponse.class);
        CustomerAssertions.assertCustomerPreferences(
            preferencesResponse,
            ApiTestData.PRIMARY_CUSTOMER_ID,
            ApiTestData.DEFAULT_LANGUAGE
        );
    }

    @Test(groups = {"regression"})
    public void invalidAccountIdReturnsNotFoundContract() {
        Response response = accountsClient.getAccountDetails(ApiTestData.INVALID_ACCOUNT_ID);
        ResponseAssertions.assertStatusCode(response, 404);

        ApiErrorResponse errorResponse = JsonUtils.fromResponse(response, ApiErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), "NOT_FOUND");
        Assert.assertEquals(errorResponse.getError().getCode(), "ACCOUNT_404");
        Assert.assertNotNull(errorResponse.getError().getMessage(), "Error message should be present.");
    }

    @Test(groups = {"regression"})
    public void unauthorizedAccountSummaryRequestIsRejected() {
        Response response = accountsClient.getAccountSummaryWithoutAuth();
        ResponseAssertions.assertStatusCode(response, 401);

        ApiErrorResponse errorResponse = JsonUtils.fromResponse(response, ApiErrorResponse.class);
        Assert.assertEquals(errorResponse.getStatus(), "UNAUTHORIZED");
        Assert.assertEquals(errorResponse.getError().getCode(), "AUTH_401");
        Assert.assertNotNull(errorResponse.getError().getMessage(), "Error message should be present.");
    }
}
