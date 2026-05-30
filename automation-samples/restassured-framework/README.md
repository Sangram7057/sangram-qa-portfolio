# Rest Assured Framework Sample

This folder demonstrates a mini Rest Assured and TestNG API automation framework with shared request specifications, reusable API clients, grouped suites, and CI sample files.

## Structure

- `pom.xml`
  Maven dependencies, build settings, and execution profiles
- `testng.xml`
  Default suite definition covering smoke, sanity, and regression
- `suites`
  Separate suite XML files for smoke, sanity, and regression runs
- `.env.example`
  Placeholder environment variables for API host, auth token, and sample IDs
- `src/test/java/.../base`
  Shared Rest Assured setup and test wiring
- `src/test/java/.../clients`
  Reusable API client classes for account and customer flows
- `src/test/java/.../config`
  Environment configuration helper
- `src/test/java/.../data`
  Public-safe sample IDs and filter values
- `src/test/java/.../specs`
  Shared request specifications for authorized and unauthorized calls
- `src/test/java/.../tests`
  Smoke, sanity, and regression API coverage
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
- URLs, IDs, and payload assumptions are generic placeholders
- The framework uses shared request specs, reusable API clients, and grouped TestNG suites to mirror real API automation work
