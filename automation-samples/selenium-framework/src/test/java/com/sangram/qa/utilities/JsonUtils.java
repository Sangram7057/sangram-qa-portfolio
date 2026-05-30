package com.sangram.qa.utilities;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;

public final class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().findAndRegisterModules();

    private JsonUtils() {
    }

    public static <T> List<T> readList(String resourcePath, Class<T[]> arrayType) {
        try {
            T[] values = OBJECT_MAPPER.readValue(ResourceUtils.openResource(resourcePath), arrayType);
            return Arrays.asList(values);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to read JSON resource: " + resourcePath, exception);
        }
    }
}
