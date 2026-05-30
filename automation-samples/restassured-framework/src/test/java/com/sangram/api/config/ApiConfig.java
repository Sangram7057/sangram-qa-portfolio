package com.sangram.api.config;

public final class ApiConfig {
    private ApiConfig() {
    }

    public static String baseUrl() {
        return System.getenv().getOrDefault("API_BASE_URL", "https://example.api.local");
    }

    public static String token() {
        return System.getenv().getOrDefault("API_TOKEN", "replace-me");
    }

    public static String defaultAccountId() {
        return System.getenv().getOrDefault("DEFAULT_ACCOUNT_ID", "ACC-10001");
    }

    public static String defaultCustomerId() {
        return System.getenv().getOrDefault("DEFAULT_CUSTOMER_ID", "CUST-10001");
    }
}
