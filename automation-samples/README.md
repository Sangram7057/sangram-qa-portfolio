# Automation Samples

This folder contains public-safe QA automation examples that reflect the kinds of work I do in UI, API, and regression testing.

## Security Note

- no live client credentials are stored in this repository
- no private endpoints are exposed
- authentication values are read from environment variables when needed
- selectors, URLs, and payloads are intentionally generic for public sharing

## Included Samples

- `playwright-framework/`
  Mini Playwright framework sample with config, page objects, grouped tests, MCP server, and Jenkinsfile
- `selenium-framework/`
  Hybrid Selenium WebDriver and TestNG framework sample with POM, Excel/JSON/CSV data, Log4j logging, Allure and Extent reporting, and CI samples
- `restassured-framework/`
  Hybrid Rest Assured and TestNG framework sample with POJO serialization, shared API specs, Allure plus Extent reporting, SLF4J logging, reusable clients, and CI pipeline samples
- `playwright-framework/tests/smoke/playwright-smoke.spec.ts`
  Framework-based Playwright smoke flow covering login, dashboard visibility, and sign-out
- `playwright-framework/mcp/playwright-mcp-server.ts`
  Framework-based MCP server that exposes Playwright smoke automation as MCP tools
- `playwright-framework/mcp/playwright-mcp-regression-sanity-server.ts`
  Framework-based MCP server that exposes Playwright sanity and regression suites as MCP tools
- `selenium-framework/master.xml`
  Master TestNG suite that runs all Selenium scenario classes and supports tag-based execution
- `selenium-framework/src/test/java/com/sangram/qa/tests/LoginTest.java`
  Framework-based Selenium smoke scenario for valid login and dashboard landing
- `selenium-framework/src/test/java/com/sangram/qa/tests/AuthenticationTest.java`
  Framework-based Selenium sanity scenario for end-to-end sign in and sign out validation
- `selenium-framework/src/test/java/com/sangram/qa/tests/AccountsTest.java`
  Framework-based Selenium module test with tagged sanity and regression scenarios for account search and title validation
- `restassured-framework/src/test/java/com/sangram/api/tests/SmokeSuiteTest.java`
  Framework-based Rest Assured smoke coverage for account summary contract validation
- `restassured-framework/src/test/java/com/sangram/api/tests/SanitySuiteTest.java`
  Framework-based Rest Assured sanity coverage using POJO request payloads and transaction filter validation
- `restassured-framework/src/test/java/com/sangram/api/tests/RegressionSuiteTest.java`
  Framework-based Rest Assured regression coverage for customer profile, preference updates, and negative API contracts
- `playwright-framework/mcp/mcp-config.sample.json`
  Framework-level MCP host configuration for connecting Playwright framework servers over stdio

## MCP Server Notes

The Playwright MCP samples follow the modern MCP server pattern:

- `McpServer` for registering tools
- `StdioServerTransport` for host communication
- `zod/v4` schemas for tool input validation
- Playwright browser automation inside tool handlers

The regression and sanity MCP example exposes separate tools:

- `run_sanity_suite`
- `run_regression_suite`

The sample is intentionally generic. In a real project, selectors, routes, and assertions would be mapped to the target application under test.

## Example Environment Variables

```text
BASE_URL=https://example.test-app.local
TEST_USERNAME=demo.user
TEST_PASSWORD=replace-me
API_BASE_URL=https://example.api.local
API_TOKEN=replace-me
```

These examples are intended to show framework structure, validation style, and safe credential handling rather than runnable client-specific code.
