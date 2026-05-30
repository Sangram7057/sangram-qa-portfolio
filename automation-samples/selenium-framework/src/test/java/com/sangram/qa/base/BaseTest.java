package com.sangram.qa.base;

import com.sangram.qa.utilities.ConfigReader;
import com.sangram.qa.utilities.WaitHelper;
import com.sangram.qa.utilities.WebDriverFactory;
import java.lang.reflect.Method;
import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest {
    protected final Logger logger = LogManager.getLogger(getClass());
    protected WebDriver driver;
    protected WaitHelper waitHelper;

    @BeforeMethod(alwaysRun = true)
    public void setUp(Method method) {
        driver = WebDriverFactory.createDriver(ConfigReader.browser(), ConfigReader.headless());
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(ConfigReader.implicitWaitSeconds()));
        waitHelper = new WaitHelper(driver, Duration.ofSeconds(ConfigReader.explicitWaitSeconds()));
        logger.info("Started WebDriver for test {}", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Closing WebDriver for {}", getClass().getSimpleName());
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        return driver;
    }
}
