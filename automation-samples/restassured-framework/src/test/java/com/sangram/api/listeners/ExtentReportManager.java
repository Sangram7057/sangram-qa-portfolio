package com.sangram.api.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IAlterSuiteListener;
import org.testng.IClassListener;
import org.testng.IConfigurable;
import org.testng.IConfigurationListener;
import org.testng.IConfigureCallBack;
import org.testng.IDataProviderInterceptor;
import org.testng.IDataProviderListener;
import org.testng.IDataProviderMethod;
import org.testng.IExecutionListener;
import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.IConfigurationAnnotation;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.IFactoryAnnotation;
import org.testng.annotations.IListenersAnnotation;
import org.testng.annotations.ITestAnnotation;
import org.testng.xml.XmlSuite;

public class ExtentReportManager implements
    ITestListener,
    ISuiteListener,
    IInvokedMethodListener,
    IExecutionListener,
    org.testng.IAnnotationTransformer,
    IReporter,
    IMethodInterceptor,
    IAlterSuiteListener,
    IConfigurationListener,
    IDataProviderListener,
    IDataProviderInterceptor,
    IHookable,
    IConfigurable,
    IClassListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtentReportManager.class);
    private static final ThreadLocal<ExtentTest> CURRENT_TEST = new ThreadLocal<>();
    private static final Object LOCK = new Object();
    private static ExtentReports extentReports;
    private static ExtentTest lifecycleNode;

    private static ExtentReports getReport() {
        synchronized (LOCK) {
            if (extentReports == null) {
                try {
                    Path reportDirectory = Paths.get("target", "extent-report");
                    Files.createDirectories(reportDirectory);

                    ExtentSparkReporter sparkReporter =
                        new ExtentSparkReporter(reportDirectory.resolve("extent-report.html").toString());
                    sparkReporter.config().setDocumentTitle("Rest Assured Hybrid Framework Report");
                    sparkReporter.config().setReportName("API Automation Execution Report");
                    sparkReporter.config().setTheme(Theme.STANDARD);

                    extentReports = new ExtentReports();
                    extentReports.attachReporter(sparkReporter);
                    extentReports.setSystemInfo("Framework", "Rest Assured Hybrid Framework");
                    extentReports.setSystemInfo("Execution Time", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

                    lifecycleNode = extentReports.createTest("Framework Lifecycle");
                } catch (Exception exception) {
                    throw new IllegalStateException("Unable to initialize Extent report infrastructure.", exception);
                }
            }
        }

        return extentReports;
    }

    private static ExtentTest getCurrentTest() {
        return CURRENT_TEST.get();
    }

    private static ExtentTest createTestNode(ITestResult result) {
        ExtentTest test = getReport().createTest(
            result.getTestClass().getName() + " :: " + result.getMethod().getMethodName()
        );

        if (result.getMethod().getDescription() != null) {
            test.assignAuthor("Sangram Shinde");
            test.info(result.getMethod().getDescription());
        }

        String[] groups = result.getMethod().getGroups();
        if (groups != null && groups.length > 0) {
            test.assignCategory(groups);
        }

        CURRENT_TEST.set(test);
        return test;
    }

    private static void logLifecycle(Status status, String message) {
        getReport();
        lifecycleNode.log(status, message);
        LOGGER.info(message);
    }

    private static void attachThrowable(ExtentTest test, Throwable throwable) {
        if (test == null || throwable == null) {
            return;
        }

        test.fail(throwable);
        StringWriter stackTrace = new StringWriter();
        throwable.printStackTrace(new PrintWriter(stackTrace));
        test.fail("<pre>" + stackTrace + "</pre>");
    }

    @Override
    public void onExecutionStart() {
        getReport();
        logLifecycle(Status.INFO, "TestNG execution started.");
    }

    @Override
    public void onExecutionFinish() {
        logLifecycle(Status.INFO, "TestNG execution finished.");
        getReport().flush();
        CURRENT_TEST.remove();
    }

    @Override
    public void onStart(ISuite suite) {
        logLifecycle(Status.INFO, "Suite started: " + suite.getName());
    }

    @Override
    public void onFinish(ISuite suite) {
        logLifecycle(Status.INFO, "Suite finished: " + suite.getName());
        getReport().flush();
    }

    @Override
    public void onStart(ITestContext context) {
        logLifecycle(Status.INFO, "Test context started: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logLifecycle(Status.INFO, "Test context finished: " + context.getName());
    }

    @Override
    public void onBeforeClass(ITestClass testClass) {
        logLifecycle(Status.INFO, "Before class: " + testClass.getName());
    }

    @Override
    public void onAfterClass(ITestClass testClass) {
        logLifecycle(Status.INFO, "After class: " + testClass.getName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = createTestNode(result);
        test.log(Status.INFO, "Test started.");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTest test = getCurrentTest();
        if (test != null) {
            test.log(Status.PASS, "Test passed.");
        }
        CURRENT_TEST.remove();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTest test = getCurrentTest();
        if (test == null) {
            test = createTestNode(result);
        }

        test.log(Status.FAIL, "Test failed.");
        attachThrowable(test, result.getThrowable());
        CURRENT_TEST.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        ExtentTest test = getCurrentTest();
        if (test == null) {
            test = createTestNode(result);
        }

        test.log(Status.SKIP, "Test skipped.");
        attachThrowable(test, result.getThrowable());
        CURRENT_TEST.remove();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        ExtentTest test = getCurrentTest();
        if (test != null) {
            test.log(Status.WARNING, "Test failed but within success percentage.");
        }
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        ExtentTest test = getCurrentTest();
        if (test == null) {
            test = createTestNode(result);
        }

        test.log(Status.FAIL, "Test failed with timeout.");
        attachThrowable(test, result.getThrowable());
        CURRENT_TEST.remove();
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && getCurrentTest() != null) {
            getCurrentTest().log(Status.INFO, "Invoking method: " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && getCurrentTest() != null) {
            getCurrentTest().log(Status.INFO, "Completed method: " + method.getTestMethod().getMethodName());
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        beforeInvocation(method, testResult);
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult, ITestContext context) {
        afterInvocation(method, testResult);
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr) {
        logLifecycle(Status.PASS, "Configuration succeeded: " + itr.getMethod().getMethodName());
    }

    @Override
    public void onConfigurationSuccess(ITestResult itr, ITestNGMethod tm) {
        logLifecycle(Status.PASS, "Configuration succeeded: " + tm.getMethodName());
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
        logLifecycle(Status.FAIL, "Configuration failed: " + itr.getMethod().getMethodName());
    }

    @Override
    public void onConfigurationFailure(ITestResult itr, ITestNGMethod tm) {
        logLifecycle(Status.FAIL, "Configuration failed: " + tm.getMethodName());
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
        logLifecycle(Status.SKIP, "Configuration skipped: " + itr.getMethod().getMethodName());
    }

    @Override
    public void onConfigurationSkip(ITestResult itr, ITestNGMethod tm) {
        logLifecycle(Status.SKIP, "Configuration skipped: " + tm.getMethodName());
    }

    @Override
    public void beforeConfiguration(ITestResult tr) {
        logLifecycle(Status.INFO, "Before configuration: " + tr.getMethod().getMethodName());
    }

    @Override
    public void beforeConfiguration(ITestResult tr, ITestNGMethod tm) {
        logLifecycle(Status.INFO, "Before configuration: " + tm.getMethodName());
    }

    @Override
    public void beforeDataProviderExecution(IDataProviderMethod dataProviderMethod, ITestNGMethod method, ITestContext iTestContext) {
        logLifecycle(Status.INFO, "Before data provider: " + dataProviderMethod.getMethod().getName());
    }

    @Override
    public void afterDataProviderExecution(IDataProviderMethod dataProviderMethod, ITestNGMethod method, ITestContext iTestContext) {
        logLifecycle(Status.INFO, "After data provider: " + dataProviderMethod.getMethod().getName());
    }

    @Override
    public void onDataProviderFailure(ITestNGMethod method, ITestContext ctx, RuntimeException t) {
        logLifecycle(Status.FAIL, "Data provider failure in: " + method.getMethodName());
    }

    @Override
    public Iterator<Object[]> intercept(
        Iterator<Object[]> original,
        IDataProviderMethod dataProviderMethod,
        ITestNGMethod method,
        ITestContext iTestContext
    ) {
        logLifecycle(Status.INFO, "Intercepting data provider rows for: " + method.getMethodName());
        return original;
    }

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        logLifecycle(Status.INFO, "Method interceptor received " + methods.size() + " methods.");
        return methods;
    }

    @Override
    public void alter(List<XmlSuite> suites) {
        suites.forEach(suite -> logLifecycle(Status.INFO, "Altering suite before execution: " + suite.getName()));
    }

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod != null && (annotation.getDescription() == null || annotation.getDescription().isBlank())) {
            annotation.setDescription("Auto-generated description for " + testMethod.getName());
        }
    }

    @Override
    public void transform(IConfigurationAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        if (testMethod != null) {
            LOGGER.debug("Configuration annotation observed for {}", testMethod.getName());
        }
    }

    @Override
    public void transform(IDataProviderAnnotation annotation, Method method) {
        if (method != null) {
            LOGGER.debug("Data provider annotation observed for {}", method.getName());
        }
    }

    @Override
    public void transform(IFactoryAnnotation annotation, Method method) {
        if (method != null) {
            LOGGER.debug("Factory annotation observed for {}", method.getName());
        }
    }

    @Override
    public void transform(IListenersAnnotation annotation, Class<?> testClass) {
        if (testClass != null) {
            LOGGER.debug("Listeners annotation observed for {}", testClass.getName());
        }
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        ExtentTest reportSummary = getReport().createTest("Execution Summary");
        reportSummary.log(Status.INFO, "Output directory: " + outputDirectory);
        reportSummary.log(Status.INFO, "Suite count: " + suites.size());
        getReport().flush();
    }

    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        if (getCurrentTest() != null) {
            getCurrentTest().log(Status.INFO, "IHookable around test method execution.");
        }
        callBack.runTestMethod(testResult);
    }

    @Override
    public void run(IConfigureCallBack callBack, ITestResult testResult) {
        logLifecycle(Status.INFO, "IConfigurable around configuration method: " + testResult.getMethod().getMethodName());
        callBack.runConfigurationMethod(testResult);
    }
}
