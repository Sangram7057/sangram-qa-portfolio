package com.sangram.api.clients;

import com.sangram.api.specs.ApiRequestSpecs;
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
}
