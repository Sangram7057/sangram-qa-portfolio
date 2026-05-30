package com.sangram.qa.utilities;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public final class ScreenshotUtils {
    private static final DateTimeFormatter FILE_SUFFIX =
        DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

    private ScreenshotUtils() {
    }

    public static byte[] captureBytes(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public static Path saveScreenshot(byte[] screenshot, String testName) {
        try {
            Path screenshotDirectory = Paths.get("target", "screenshots");
            Files.createDirectories(screenshotDirectory);
            Path screenshotPath =
                screenshotDirectory.resolve(testName + "-" + LocalDateTime.now().format(FILE_SUFFIX) + ".png");
            Files.write(screenshotPath, screenshot);
            return screenshotPath;
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to save screenshot for " + testName, exception);
        }
    }
}
