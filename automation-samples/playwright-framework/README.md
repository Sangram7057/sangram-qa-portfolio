# Playwright Framework Sample

This folder demonstrates a mini Playwright automation framework with realistic project structure, reusable page objects, environment configuration, grouped suites, and multiple MCP server samples.

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
- `src/pages`
  Page Object Model classes
- `tests`
  Smoke, sanity, and regression coverage
- `tests/smoke/playwright-smoke.spec.ts`
  Framework-based smoke sample covering login, dashboard widgets, and sign-out
- `mcp`
  MCP server sample exposing framework flows as MCP tools
- `mcp/playwright-mcp-server.ts`
  Framework-based Playwright smoke server exposed over MCP stdio transport
- `mcp/playwright-mcp-regression-sanity-server.ts`
  Framework-based MCP server with separate sanity and regression tools
- `Jenkinsfile`
  CI pipeline sample

## Example Commands

```text
npm ci
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
- This sample is designed to look and read like a practical team framework
