import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumRegressionSanitySuite {
    private WebDriver driver;
    private WebDriverWait wait;

    private final String baseUrl =
        System.getenv().getOrDefault("BASE_URL", "https://example.test-app.local");
    private final String username =
        System.getenv().getOrDefault("TEST_USERNAME", "demo.user");
    private final String password =
        System.getenv().getOrDefault("TEST_PASSWORD", "replace-me");

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    private void login() {
        driver.get(baseUrl + "/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    @Test(groups = {"sanity"})
    public void sanitySuiteCriticalJourney() {
        login();

        WebElement dashboardTitle =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='dashboard-title']")));
        WebElement accountSummary =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='account-summary-card']")));

        Assert.assertEquals(dashboardTitle.getText(), "Dashboard");
        Assert.assertTrue(accountSummary.isDisplayed(), "Account summary must be visible in sanity suite.");
    }

    @Test(groups = {"regression"})
    public void regressionSuiteAccountAndTransactionFlows() {
        login();

        driver.get(baseUrl + "/accounts");
        WebElement accountsHeading =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='accounts-title']")));
        Assert.assertEquals(accountsHeading.getText(), "Accounts");

        WebElement searchBox =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='account-search']")));
        searchBox.sendKeys("primary");

        WebElement accountGrid =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='account-grid']")));
        Assert.assertTrue(accountGrid.isDisplayed(), "Account grid must remain visible after search.");

        driver.get(baseUrl + "/transactions");
        WebElement dateRange =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='date-range']")));
        new Select(dateRange).selectByValue("last-30-days");
        driver.findElement(By.cssSelector("[data-testid='apply-filters']")).click();

        WebElement transactionsTable =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='transactions-table']")));
        Assert.assertTrue(transactionsTable.isDisplayed(), "Transactions table must be visible after filtering.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
