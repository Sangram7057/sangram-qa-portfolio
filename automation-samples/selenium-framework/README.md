# Selenium Framework Sample

This folder demonstrates a mini Selenium WebDriver and TestNG framework with page objects, grouped suites, Maven configuration, and CI sample files.

## Structure

- `pom.xml`
  Maven dependencies and build settings
- `testng.xml`
  Suite definition for grouped runs
- `.env.example`
  Placeholder environment variables
- `src/test/java/.../base`
  Shared test setup and teardown
- `src/test/java/.../config`
  Environment configuration helper
- `src/test/java/.../pages`
  Page Object Model classes
- `src/test/java/.../tests`
  Sanity and regression suite classes
- `Jenkinsfile`
  CI pipeline sample

## Example Commands

```text
mvn test -Dgroups=sanity
mvn test -Dgroups=regression
```

## Notes

- No client credentials are stored in this repository
- URLs and locators are generic placeholders
- The suite is structured to look like a reusable team framework
