# Selenium Framework Sample

This folder demonstrates a mini Selenium WebDriver and TestNG framework with page objects, shared driver setup, suite-based execution, and CI sample files.

## Structure

- `pom.xml`
  Maven dependencies, build settings, and execution profiles
- `testng.xml`
  Default suite definition covering smoke, sanity, and regression
- `suites`
  Separate suite XML files for smoke, sanity, and regression runs
- `.env.example`
  Placeholder environment variables including headless execution
- `src/test/java/.../base`
  Shared test setup and teardown
- `src/test/java/.../config`
  Environment configuration helper
- `src/test/java/.../data`
  Public-safe credential and test user data access
- `src/test/java/.../factory`
  Browser driver creation and configuration
- `src/test/java/.../pages`
  Page Object Model classes
- `src/test/java/.../utils`
  Wait helpers and reusable UI synchronization
- `src/test/java/.../tests`
  Smoke, sanity, and regression suite classes
- `Jenkinsfile`
  CI pipeline sample

## Example Commands

```text
mvn test -Psmoke
mvn test -Psanity
mvn test -Pregression
mvn test
```

## Notes

- No client credentials are stored in this repository
- URLs and locators are generic placeholders
- The framework uses shared page objects, a driver factory, explicit waits, and suite XMLs to mirror real project structure
