package com.sangram.qa.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

public final class WebDriverFactory {
    private static final Logger LOGGER = LogManager.getLogger(WebDriverFactory.class);

    private WebDriverFactory() {
    }

    public static WebDriver createDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "edge" -> createEdgeDriver(headless);
            case "chrome" -> createChromeDriver(headless);
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1440,900");
        if (headless) {
            options.addArguments("--headless=new");
        }
        LOGGER.info("Launching Chrome driver with headless={}", headless);
        return new ChromeDriver(options);
    }

    private static WebDriver createEdgeDriver(boolean headless) {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--window-size=1440,900");
        if (headless) {
            options.addArguments("--headless=new");
        }
        LOGGER.info("Launching Edge driver with headless={}", headless);
        return new EdgeDriver(options);
    }
}
