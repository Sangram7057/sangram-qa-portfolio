# Playwright Framework Sample

This folder demonstrates a mini Playwright automation framework with realistic project structure, reusable page objects, environment configuration, grouped suites, and an MCP server sample.

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
- `mcp`
  MCP server sample exposing framework flows as MCP tools
- `Jenkinsfile`
  CI pipeline sample

## Example Commands

```text
npm ci
npm run test:smoke
npm run test:sanity
npm run test:regression
npm run mcp
```

## Notes

- No real credentials are stored here
- URLs and selectors are intentionally generic
- This sample is designed to look and read like a practical team framework
