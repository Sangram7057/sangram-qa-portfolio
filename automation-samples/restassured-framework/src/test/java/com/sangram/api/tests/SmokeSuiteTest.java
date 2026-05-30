package com.sangram.api.tests;

import com.sangram.api.assertions.AccountAssertions;
import com.sangram.api.assertions.ResponseAssertions;
import com.sangram.api.base.ApiTestBase;
import com.sangram.api.models.response.AccountSummaryResponse;
import com.sangram.api.utils.JsonUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class SmokeSuiteTest extends ApiTestBase {
    @Test(groups = {"smoke"})
    public void accountSummaryReturnsExpectedContract() {
        Response response = accountsClient.getAccountSummary();
        ResponseAssertions.assertStatusCode(response, 200);

        AccountSummaryResponse summaryResponse = JsonUtils.fromResponse(response, AccountSummaryResponse.class);
        AccountAssertions.assertSummaryContract(summaryResponse);
    }

    @Test(groups = {"smoke"})
    public void accountSummaryIncludesPrimaryBalanceWidgets() {
        Response response = accountsClient.getAccountSummary();
        ResponseAssertions.assertStatusCode(response, 200);

        AccountSummaryResponse summaryResponse = JsonUtils.fromResponse(response, AccountSummaryResponse.class);
        AccountAssertions.assertPrimaryBalanceWidgets(summaryResponse);
    }
}
