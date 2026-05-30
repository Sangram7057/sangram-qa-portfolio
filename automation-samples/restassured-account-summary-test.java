import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.notNullValue;

public class RestAssuredAccountSummaryTest {
    private final String apiBaseUrl =
        System.getenv().getOrDefault("API_BASE_URL", "https://example.api.local");
    private final String apiToken =
        System.getenv().getOrDefault("API_TOKEN", "replace-me");

    @BeforeClass
    public void configureClient() {
        RestAssured.baseURI = apiBaseUrl;
    }

    @Test
    public void accountSummaryReturnsExpectedContract() {
        given()
            .contentType(ContentType.JSON)
            .header("Authorization", "Bearer " + apiToken)
        .when()
            .get("/api/accounts/summary")
        .then()
            .statusCode(200)
            .body("status", equalTo("SUCCESS"))
            .body("data.customerId", notNullValue())
            .body("data.accounts.size()", greaterThanOrEqualTo(1))
            .body("data.accounts[0].currency", notNullValue());
    }
}
