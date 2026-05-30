package com.sangram.qa.base;

import com.sangram.qa.config.EnvConfig;
import com.sangram.qa.factory.DriverFactory;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import com.sangram.qa.utils.WaitHelper;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class TestBase {
    protected WebDriver driver;
    protected WaitHelper waitHelper;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createChromeDriver(EnvConfig.headless());
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        waitHelper = new WaitHelper(driver, Duration.ofSeconds(EnvConfig.explicitWaitSeconds()));
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
