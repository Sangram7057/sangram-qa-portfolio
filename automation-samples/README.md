# Automation Samples

This folder contains public-safe QA automation examples that reflect the kinds of work I do in UI, API, and regression testing.

## Security Note

- no live client credentials are stored in this repository
- no private endpoints are exposed
- authentication values are read from environment variables when needed
- selectors, URLs, and payloads are intentionally generic for public sharing

## Included Samples

- `playwright-smoke.spec.ts`
  Example Playwright smoke flow using environment variables for login data
- `selenium-login-smoke-test.java`
  Example Selenium WebDriver and TestNG smoke flow with waits and assertions
- `restassured-account-summary-test.java`
  Example Rest Assured API test using a bearer token from the environment

## Example Environment Variables

```text
BASE_URL=https://example.test-app.local
TEST_USERNAME=demo.user
TEST_PASSWORD=replace-me
API_BASE_URL=https://example.api.local
API_TOKEN=replace-me
```

These examples are intended to show framework structure, validation style, and safe credential handling rather than runnable client-specific code.
