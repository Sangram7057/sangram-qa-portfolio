# Selenium Framework Sample

This folder demonstrates a hybrid Selenium WebDriver and TestNG framework with Page Object Model classes, data-driven utilities, Allure and Extent reporting, Log4j logging, shared driver setup, and CI sample files.

## Structure

- `pom.xml`
  Maven dependencies, reporting plugins, build settings, and execution profiles
- `master.xml`
  Default suite definition that runs all tagged Selenium scenario classes
- `suites`
  Separate suite XML files that filter `smoke`, `sanity`, and `regression` tags from the same scenario classes
- `.env.example`
  Placeholder environment variables including headless execution
- `ci`
  GitHub Actions sample for framework compilation and suite execution
- `src/test/java/.../base`
  Shared driver setup and teardown for UI tests
- `src/test/java/.../pages`
  Page Object Model classes
- `src/test/java/.../listeners`
  TestNG listener with screenshot capture and report integration
- `src/test/java/.../models`
  Typed data models for Excel, JSON, and CSV-driven tests
- `src/test/java/.../utilities`
  Config reader, Excel/JSON/CSV readers, screenshots, reporting, waits, and data providers
- `src/test/java/.../tests`
  Separate scenario-based smoke, sanity, and regression test classes using data providers
- `src/test/resources`
  `config.properties`, `log4j2.xml`, `allure.properties`, JSON/CSV data, and `testdata.xlsx`
- `Jenkinsfile`
  CI pipeline sample

## Example Commands

```text
mvn test -Psmoke
mvn test -Psanity
mvn test -Pregression
mvn test
mvn test -DsuiteXmlFile=master.xml
mvn allure:serve
```

## Notes

- No client credentials are stored in this repository
- URLs and locators are generic placeholders
- The framework uses Page Object Model classes, Excel/JSON/CSV-driven test data, separate scenario-based test classes, Log4j logging, Allure and Extent reporting, and a `master.xml` plus group-filtered suite XMLs to mirror real project structure
