# Rest Assured Framework Sample

This folder demonstrates a hybrid Rest Assured and TestNG API automation framework with shared request specifications, POJO serialization, reusable API clients, reporting, structured logging, and CI sample files.

## Structure

- `pom.xml`
  Maven dependencies, build settings, reporting plugins, and execution profiles
- `testng.xml`
  Default suite definition covering smoke, sanity, and regression
- `suites`
  Separate suite XML files for smoke, sanity, and regression runs
- `.env.example`
  Placeholder environment variables for API host, auth token, and sample IDs
- `src/test/java/.../base`
  Shared Rest Assured setup and test wiring
- `src/test/java/.../assertions`
  Reusable response and domain assertion helpers
- `src/test/java/.../builders`
  Request builders for safe, readable payload creation
- `src/test/java/.../clients`
  Reusable API client classes for account and customer flows
- `src/test/java/.../config`
  Environment configuration helper
- `src/test/java/.../data`
  Public-safe sample IDs and filter values
- `src/test/java/.../filters`
  SLF4J-backed request and response logging
- `src/test/java/.../listeners`
  TestNG listener for Allure attachments and lifecycle logging
- `src/test/java/.../models`
  Request and response POJO classes for Jackson serialization
- `src/test/java/.../specs`
  Shared request specifications for authorized and unauthorized calls
- `src/test/java/.../tests`
  Smoke, sanity, and regression API coverage
- `src/test/java/.../utils`
  Shared JSON serialization helpers
- `src/test/resources`
  Allure and Logback configuration
- `ci`
  GitHub Actions and Azure DevOps sample pipeline definitions
- `Jenkinsfile`
  CI pipeline sample

## Example Commands

```text
mvn test -Psmoke
mvn test -Psanity
mvn test -Pregression
mvn test
mvn allure:serve
```

## Notes

- No client credentials are stored in this repository
- URLs, IDs, and payload assumptions are generic placeholders
- The framework uses shared request specs, Jackson-based POJO serialization, reusable API clients, Allure reporting, SLF4J logging, and grouped TestNG suites to mirror real API automation work
