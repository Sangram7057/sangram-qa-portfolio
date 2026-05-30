package com.sangram.api.assertions;

import io.restassured.response.Response;
import org.testng.Assert;

public final class ResponseAssertions {
    private ResponseAssertions() {
    }

    public static void assertStatusCode(Response response, int expectedStatusCode) {
        Assert.assertEquals(response.getStatusCode(), expectedStatusCode, "Unexpected HTTP status code.");
    }
}
