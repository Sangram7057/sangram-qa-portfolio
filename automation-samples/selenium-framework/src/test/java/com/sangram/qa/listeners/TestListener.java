package com.sangram.qa.listeners;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.sangram.qa.base.BaseTest;
import com.sangram.qa.utilities.ExtentReportManager;
import com.sangram.qa.utilities.ScreenshotUtils;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = LogManager.getLogger(TestListener.class);

    @Override
    public void onStart(ITestContext context) {
        ExtentReportManager.getReporter();
        LOGGER.info("Starting Selenium suite {}", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentReportManager.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
        LOGGER.info("Starting test {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.getTest().pass("Test passed");
        LOGGER.info("Test passed {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test failed {}", result.getMethod().getMethodName(), result.getThrowable());
        ExtentReportManager.getTest().fail(result.getThrowable());

        if (result.getInstance() instanceof BaseTest baseTest) {
            WebDriver driver = baseTest.getDriver();
            if (driver != null) {
                byte[] screenshot = ScreenshotUtils.captureBytes(driver);
                Path screenshotPath = ScreenshotUtils.saveScreenshot(screenshot, result.getMethod().getMethodName());

                Allure.addAttachment(
                    "Failure Screenshot",
                    "image/png",
                    new ByteArrayInputStream(screenshot),
                    ".png"
                );

                try {
                    ExtentReportManager.getTest().fail(
                        "Failure screenshot",
                        MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath.toString()).build()
                    );
                } catch (Exception exception) {
                    LOGGER.warn("Could not attach screenshot to Extent report", exception);
                }
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentReportManager.getTest().skip("Test skipped");
        LOGGER.warn("Test skipped {}", result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {
        ExtentReportManager.flush();
        LOGGER.info("Finished Selenium suite {}", context.getName());
    }
}
