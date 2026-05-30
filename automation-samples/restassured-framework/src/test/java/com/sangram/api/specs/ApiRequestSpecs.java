package com.sangram.api.specs;

import com.sangram.api.config.ApiConfig;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiRequestSpecs {
    private ApiRequestSpecs() {
    }

    public static RequestSpecification authorizedJson() {
        return new RequestSpecBuilder()
            .setBaseUri(ApiConfig.baseUrl())
            .setContentType(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + ApiConfig.token())
            .build();
    }

    public static RequestSpecification unauthorizedJson() {
        return new RequestSpecBuilder()
            .setBaseUri(ApiConfig.baseUrl())
            .setContentType(ContentType.JSON)
            .build();
    }
}
