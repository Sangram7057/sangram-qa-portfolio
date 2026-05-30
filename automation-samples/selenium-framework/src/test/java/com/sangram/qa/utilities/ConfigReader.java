package com.sangram.qa.utilities;

import java.io.InputStream;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConfigReader {
    private static final Logger LOGGER = LogManager.getLogger(ConfigReader.class);
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream =
                 ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (inputStream == null) {
                throw new IllegalStateException("config.properties was not found in test resources.");
            }
            PROPERTIES.load(inputStream);
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to load Selenium framework configuration.", exception);
        }
    }

    private ConfigReader() {
    }

    public static String browser() {
        return read("browser", "BROWSER", "chrome");
    }

    public static String baseUrl() {
        return read("base.url", "BASE_URL", "https://example.test-app.local");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(read("headless", "HEADLESS", "true"));
    }

    public static long implicitWaitSeconds() {
        return Long.parseLong(read("implicit.wait.seconds", "IMPLICIT_WAIT_SECONDS", "5"));
    }

    public static long explicitWaitSeconds() {
        return Long.parseLong(read("explicit.wait.seconds", "EXPLICIT_WAIT_SECONDS", "15"));
    }

    public static String defaultUsername() {
        return read("default.username", "TEST_USERNAME", "demo.user");
    }

    public static String defaultPassword() {
        return read("default.password", "TEST_PASSWORD", "replace-me");
    }

    public static String reportName() {
        return read("report.name", "REPORT_NAME", "Selenium Hybrid Framework Report");
    }

    private static String read(String propertyKey, String envKey, String fallback) {
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }

        String propertyValue = PROPERTIES.getProperty(propertyKey);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }

        LOGGER.debug("Using fallback value for property {}", propertyKey);
        return fallback;
    }
}
