# Sangram Shinde - GitHub QA Portfolio

This repository is a GitHub-ready portfolio for QA and test automation work. It is designed to present your experience through public, NDA-safe case studies and sample QA artifacts.

The portfolio is NDA-safe:

- client-sensitive details are generalized
- the focus is on test strategy, automation skills, and delivery outcomes
- sample artifacts show how you work without exposing employer code

## Portfolio Snapshot

- 4+ years in software testing across banking and energy utilities applications
- Hands-on expertise in Selenium WebDriver, Playwright, Java, TypeScript, TestNG, Rest Assured, SQL, Jenkins, and MCP-based workflows
- Reduced manual testing effort by 40% through automation
- Experience across regression, smoke, sanity, API, database, ETL, system, integration, and UAT testing

## What Is Included

- `index.html`
  GitHub Pages landing page for your portfolio
- `styles.css`
  Styling for the portfolio site
- `automation-samples/`
  Credential-free UI and API automation code samples for public review
- `case-studies/`
  NDA-safe project summaries based on your experience
- `artifacts/`
  Sample QA deliverables that clients can review
- `GITHUB_PROFILE_TEMPLATE.md`
  Optional template for your personal GitHub profile README

## Suggested Repository Names

- `sangram-qa-portfolio`
- `qa-automation-portfolio`
- `sangram-shinde-portfolio`

## How To Publish On GitHub

1. Create a GitHub account if you do not have one yet.
2. Create a new public repository using one of the names above.
3. Upload all files from this folder into that repository.
4. Open the repository `Settings` -> `Pages`.
5. Under `Build and deployment`, choose:
   - Source: `Deploy from a branch`
   - Branch: `main`
   - Folder: `/ (root)`
6. Save the settings.
7. GitHub will generate a live link like:
   - `https://your-username.github.io/sangram-qa-portfolio/`

## Suggested Portfolio Introduction

I am a Software Test Engineer with 4+ years of experience in automation and manual testing across banking and energy utilities applications. My work includes web testing, API validation, database checks, ETL and migration support, CI/CD execution, release validation, and test framework improvement using Selenium WebDriver, Playwright, Java, TypeScript, TestNG, Rest Assured, SQL, Jenkins, and MCP-based workflows. This repository is a public NDA-safe portfolio that reflects my resume through selected project summaries, sample QA artifacts, and practical quality engineering deliverables.

## Code Samples

This portfolio also includes public-safe automation code examples in [automation-samples](./automation-samples):

- Playwright mini framework sample with page objects, config, MCP server, and Jenkinsfile
- Framework-based Playwright smoke coverage under `playwright-framework/tests/smoke/playwright-smoke.spec.ts`
- Framework-based Playwright MCP smoke server under `playwright-framework/mcp/playwright-mcp-server.ts`
- Framework-based Playwright MCP sanity and regression server under `playwright-framework/mcp/playwright-mcp-regression-sanity-server.ts`
- Selenium WebDriver and TestNG mini framework sample with Maven, grouped suites, and Jenkinsfile
- Framework-based Selenium smoke suite under `selenium-framework/src/test/java/com/sangram/qa/tests/SmokeSuiteTest.java`
- Framework-based Selenium sanity suite under `selenium-framework/src/test/java/com/sangram/qa/tests/SanitySuiteTest.java`
- Framework-based Selenium regression suite under `selenium-framework/src/test/java/com/sangram/qa/tests/RegressionSuiteTest.java`
- Rest Assured hybrid framework sample with POJO serialization, shared request specs, reusable clients, Allure reporting, SLF4J logging, and CI pipeline samples
- Framework-based Rest Assured smoke suite under `restassured-framework/src/test/java/com/sangram/api/tests/SmokeSuiteTest.java`
- Framework-based Rest Assured sanity suite under `restassured-framework/src/test/java/com/sangram/api/tests/SanitySuiteTest.java`
- Framework-based Rest Assured regression suite under `restassured-framework/src/test/java/com/sangram/api/tests/RegressionSuiteTest.java`

All examples avoid hardcoded credentials and use environment variables or placeholders instead.
