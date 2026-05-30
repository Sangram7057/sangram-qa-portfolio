package com.sangram.qa.config;

public final class EnvConfig {
    private EnvConfig() {
    }

    public static String baseUrl() {
        return System.getenv().getOrDefault("BASE_URL", "https://example.test-app.local");
    }

    public static String username() {
        return System.getenv().getOrDefault("TEST_USERNAME", "demo.user");
    }

    public static String password() {
        return System.getenv().getOrDefault("TEST_PASSWORD", "replace-me");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getenv().getOrDefault("HEADLESS", "true"));
    }

    public static long explicitWaitSeconds() {
        return Long.parseLong(System.getenv().getOrDefault("EXPLICIT_WAIT_SECONDS", "15"));
    }
}
