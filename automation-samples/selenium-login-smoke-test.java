import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SeleniumLoginSmokeTest {
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

    @Test
    public void userCanLoginAndOpenDashboard() {
        driver.get(baseUrl + "/login");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));

        WebElement dashboardTitle =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='dashboard-title']")));
        WebElement quickActions =
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-testid='quick-actions']")));

        Assert.assertEquals(dashboardTitle.getText(), "Dashboard");
        Assert.assertTrue(quickActions.isDisplayed(), "Quick actions section should be visible after login.");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
