package com.sangram.api.specs;

import com.sangram.api.config.ApiConfig;
import com.sangram.api.filters.RequestResponseLoggingFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class ApiRequestSpecs {
    private static final RequestResponseLoggingFilter LOGGING_FILTER = new RequestResponseLoggingFilter();
    private static final AllureRestAssured ALLURE_FILTER = new AllureRestAssured();

    private ApiRequestSpecs() {
    }

    public static RequestSpecification authorizedJson() {
        return new RequestSpecBuilder()
            .setBaseUri(ApiConfig.baseUrl())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addHeader("Authorization", "Bearer " + ApiConfig.token())
            .addFilter(LOGGING_FILTER)
            .addFilter(ALLURE_FILTER)
            .build();
    }

    public static RequestSpecification unauthorizedJson() {
        return new RequestSpecBuilder()
            .setBaseUri(ApiConfig.baseUrl())
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .addFilter(LOGGING_FILTER)
            .addFilter(ALLURE_FILTER)
            .build();
    }
}
