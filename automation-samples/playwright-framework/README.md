# Playwright Framework Sample

This folder demonstrates a mini Playwright automation framework with realistic project structure, reusable page objects, environment configuration, utility helpers, listener-style reporting, module-based scenario tests, tag-driven execution, and multiple MCP server samples.

## Structure

- `package.json`
  Project metadata and scripts
- `playwright.config.ts`
  Playwright runtime configuration
- `.env.example`
  Placeholder environment variables
- `src/config`
  Environment loading and shared configuration
- `src/data`
  Public-safe test data
- `src/listeners`
  Custom Playwright execution listener implemented through the Reporter API
- `src/pages`
  Page Object Model classes
- `src/utils`
  Shared utilities for tags, artifact directories, timestamps, and report output helpers
- `src/workflows`
  Reusable login, module-navigation, and regression helper flows shared by tests and MCP tools
- `tests`
  Separate module-focused scenario specs tagged with `@smoke`, `@sanity`, and `@regression`
- `tests/LoginTest.spec.ts`
  Smoke coverage for valid login and dashboard landing
- `tests/AuthenticationTest.spec.ts`
  Sanity coverage for the full authentication critical path
- `tests/AccountsTest.spec.ts`
  Tagged sanity and regression scenarios for account search coverage
- `tests/TransactionsTest.spec.ts`
  Tagged sanity and regression scenarios for transaction filtering coverage
- `mcp`
  MCP server sample exposing framework flows as MCP tools
- `mcp/playwright-mcp-server.ts`
  Framework-based Playwright smoke server exposed over MCP stdio transport
- `mcp/playwright-mcp-regression-sanity-server.ts`
  Framework-based MCP server with separate sanity and regression tools
- `Jenkinsfile`
  CI pipeline sample
- `test-results/listener-artifacts`
  Listener-generated execution summaries written per run

## Example Commands

```text
npm ci
npm test
npm run test:smoke
npm run test:sanity
npm run test:regression
npm run mcp
npm run mcp:smoke
npm run mcp:regression-sanity
```

## Notes

- No real credentials are stored here
- URLs and selectors are intentionally generic
- `npm test` acts as the main entry point and runs all module-based specs
- Playwright uses a custom reporter as the closest equivalent to a TestNG-style listener
- This sample is designed to look and read like a practical team framework
