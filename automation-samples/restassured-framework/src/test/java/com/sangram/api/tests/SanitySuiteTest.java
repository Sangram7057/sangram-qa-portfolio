package com.sangram.api.tests;

import com.sangram.api.assertions.AccountAssertions;
import com.sangram.api.assertions.ResponseAssertions;
import com.sangram.api.assertions.TransactionAssertions;
import com.sangram.api.base.ApiTestBase;
import com.sangram.api.builders.TransactionSearchRequestBuilder;
import com.sangram.api.data.ApiTestData;
import com.sangram.api.models.request.TransactionSearchRequest;
import com.sangram.api.models.response.AccountDetailsResponse;
import com.sangram.api.models.response.TransactionsResponse;
import com.sangram.api.utils.JsonUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class SanitySuiteTest extends ApiTestBase {
    @Test(groups = {"sanity"})
    public void accountDetailsReturnsOwnerAndBalanceFields() {
        Response response = accountsClient.getAccountDetails(ApiTestData.PRIMARY_ACCOUNT_ID);
        ResponseAssertions.assertStatusCode(response, 200);

        AccountDetailsResponse accountDetailsResponse = JsonUtils.fromResponse(response, AccountDetailsResponse.class);
        AccountAssertions.assertAccountDetails(accountDetailsResponse, ApiTestData.PRIMARY_ACCOUNT_ID);
    }

    @Test(groups = {"sanity"})
    public void transactionSearchSupportsSerializedFilterPayload() {
        TransactionSearchRequest request = new TransactionSearchRequestBuilder()
            .dateRange(ApiTestData.LAST_30_DAYS)
            .transactionType(ApiTestData.CREDIT)
            .page(1)
            .pageSize(20)
            .build();

        Response response = accountsClient.searchTransactions(ApiTestData.PRIMARY_ACCOUNT_ID, request);
        ResponseAssertions.assertStatusCode(response, 200);

        TransactionsResponse transactionsResponse = JsonUtils.fromResponse(response, TransactionsResponse.class);
        TransactionAssertions.assertTransactionFilterContract(transactionsResponse, ApiTestData.LAST_30_DAYS);
    }
}
