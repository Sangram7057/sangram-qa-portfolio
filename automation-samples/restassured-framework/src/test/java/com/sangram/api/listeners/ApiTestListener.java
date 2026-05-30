package com.sangram.api.listeners;

import io.qameta.allure.Allure;
import java.io.PrintWriter;
import java.io.StringWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiTestListener implements ITestListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiTestListener.class);

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("Starting suite: {}", context.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Starting test: {}", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test passed: {}", result.getMethod().getMethodName());
        Allure.addAttachment("Test Result", result.getMethod().getMethodName() + " passed successfully.");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.error("Test failed: {}", result.getMethod().getMethodName(), result.getThrowable());
        Allure.addAttachment("Failed Test", result.getMethod().getMethodName());

        if (result.getThrowable() != null) {
            StringWriter stackTrace = new StringWriter();
            result.getThrowable().printStackTrace(new PrintWriter(stackTrace));
            Allure.addAttachment("Failure Stack Trace", stackTrace.toString());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warn("Test skipped: {}", result.getMethod().getMethodName());
    }
}
