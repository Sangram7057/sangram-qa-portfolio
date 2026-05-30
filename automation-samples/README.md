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
  Mini Selenium WebDriver and TestNG framework sample with Maven, page objects, grouped suites, and Jenkinsfile
- `playwright-framework/tests/smoke/playwright-smoke.spec.ts`
  Framework-based Playwright smoke flow covering login, dashboard visibility, and sign-out
- `playwright-framework/mcp/playwright-mcp-server.ts`
  Framework-based MCP server that exposes Playwright smoke automation as MCP tools
- `playwright-framework/mcp/playwright-mcp-regression-sanity-server.ts`
  Framework-based MCP server that exposes Playwright sanity and regression suites as MCP tools
- `selenium-login-smoke-test.java`
  Example Selenium WebDriver and TestNG smoke flow with waits and assertions
- `selenium-regression-sanity-suite.java`
  Example Selenium WebDriver and TestNG suite with separate sanity and regression coverage
- `restassured-account-summary-test.java`
  Example Rest Assured API test using a bearer token from the environment
- `mcp-config.sample.json`
  Example repository-level MCP host configuration for connecting the Playwright framework servers over stdio

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
