package com.sangram.qa.utilities;

import java.io.InputStream;

public final class ResourceUtils {
    private ResourceUtils() {
    }

    public static InputStream openResource(String resourcePath) {
        InputStream inputStream = ResourceUtils.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new IllegalStateException("Resource not found: " + resourcePath);
        }
        return inputStream;
    }
}
