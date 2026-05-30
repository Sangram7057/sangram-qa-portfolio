package com.sangram.api.clients;

import com.sangram.api.models.request.CustomerPreferencesRequest;
import com.sangram.api.specs.ApiRequestSpecs;
import com.sangram.api.utils.JsonUtils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CustomersClient {
    public Response getCustomerProfile(String customerId) {
        return given()
            .spec(ApiRequestSpecs.authorizedJson())
            .pathParam("customerId", customerId)
        .when()
            .get("/api/customers/{customerId}/profile");
    }

    public Response updateCustomerPreferences(String customerId, CustomerPreferencesRequest request) {
        return given()
            .spec(ApiRequestSpecs.authorizedJson())
            .pathParam("customerId", customerId)
            .body(JsonUtils.toJson(request))
        .when()
            .put("/api/customers/{customerId}/preferences");
    }
}
