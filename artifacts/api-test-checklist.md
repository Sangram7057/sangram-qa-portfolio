# API Test Checklist

## Coverage Areas

### Authentication

- valid token accepted
- invalid token rejected
- expired token rejected
- unauthorized requests return expected status code

### Contract Validation

- required fields present
- response schema matches expectation
- data types are correct
- optional fields handled correctly

### Functional Checks

- correct status codes returned
- response body matches business behavior
- filters and query parameters work as expected
- pagination behaves correctly where applicable

### Negative Scenarios

- missing required parameters
- invalid payload structure
- invalid field values
- unsupported method or endpoint access

### Data Integrity

- created records persist correctly
- updated records reflect expected changes
- deleted or inactive records behave correctly
- API response matches database state where relevant

### Reliability

- repeat requests behave consistently
- error messages are meaningful
- timeout handling is clear
- retry behavior is understood if applicable

### Performance Observation

- normal responses complete within expected time
- heavy responses do not fail unexpectedly
- latency spikes are recorded for investigation
