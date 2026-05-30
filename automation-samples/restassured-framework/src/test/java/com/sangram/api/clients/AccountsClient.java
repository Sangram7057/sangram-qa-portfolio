package com.sangram.api.clients;

import com.sangram.api.specs.ApiRequestSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AccountsClient {
    public Response getAccountSummary() {
        return given()
            .spec(ApiRequestSpecs.authorizedJson())
        .when()
            .get("/api/accounts/summary");
    }

    public Response getAccountDetails(String accountId) {
        return given()
            .spec(ApiRequestSpecs.authorizedJson())
            .pathParam("accountId", accountId)
        .when()
            .get("/api/accounts/{accountId}");
    }

    public Response getTransactions(String accountId, String dateRange, String transactionType) {
        return given()
            .spec(ApiRequestSpecs.authorizedJson())
            .pathParam("accountId", accountId)
            .queryParam("dateRange", dateRange)
            .queryParam("transactionType", transactionType)
        .when()
            .get("/api/accounts/{accountId}/transactions");
    }

    public Response getAccountSummaryWithoutAuth() {
        return given()
            .spec(ApiRequestSpecs.unauthorizedJson())
        .when()
            .get("/api/accounts/summary");
    }
}
