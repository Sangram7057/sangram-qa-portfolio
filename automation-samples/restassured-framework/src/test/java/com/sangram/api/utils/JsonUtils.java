package com.sangram.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;

public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    private JsonUtils() {
    }

    public static String toJson(Object value) {
        try {
            return OBJECT_MAPPER.writeValueAsString(value);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to serialize request object.", exception);
        }
    }

    public static <T> T fromResponse(Response response, Class<T> targetType) {
        try {
            return OBJECT_MAPPER.readValue(response.asString(), targetType);
        } catch (Exception exception) {
            throw new IllegalStateException("Failed to deserialize API response.", exception);
        }
    }
}
