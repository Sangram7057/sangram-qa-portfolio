package com.sangram.qa.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ExtentReportManager {
    private static final ThreadLocal<ExtentTest> TEST = new ThreadLocal<>();
    private static ExtentReports extentReports;

    private ExtentReportManager() {
    }

    public static synchronized ExtentReports getReporter() {
        if (extentReports == null) {
            Path reportDirectory = Paths.get("target", "extent-report");
            try {
                Files.createDirectories(reportDirectory);
            } catch (Exception exception) {
                throw new IllegalStateException("Unable to create Extent report directory.", exception);
            }

            ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(reportDirectory.resolve("selenium-framework-report.html").toString());
            sparkReporter.config().setReportName(ConfigReader.reportName());
            sparkReporter.config().setDocumentTitle("Selenium Hybrid Framework Report");

            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            extentReports.setSystemInfo("Framework", "Selenium Hybrid Framework");
            extentReports.setSystemInfo("Browser", ConfigReader.browser());
        }
        return extentReports;
    }

    public static void createTest(String name, String description) {
        ExtentTest extentTest = getReporter().createTest(name, description);
        TEST.set(extentTest);
    }

    public static ExtentTest getTest() {
        return TEST.get();
    }

    public static synchronized void flush() {
        if (extentReports != null) {
            extentReports.flush();
        }
    }
}
