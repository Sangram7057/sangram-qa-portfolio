package com.sangram.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestResponseLoggingFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

    @Override
    public Response filter(
        FilterableRequestSpecification requestSpecification,
        FilterableResponseSpecification responseSpecification,
        FilterContext context
    ) {
        LOGGER.info("API Request -> {} {}", requestSpecification.getMethod(), requestSpecification.getURI());
        logHeaders(requestSpecification.getHeaders());

        if (requestSpecification.getBody() != null) {
            LOGGER.debug("Request body -> {}", String.valueOf(requestSpecification.getBody()));
        }

        Response response = context.next(requestSpecification, responseSpecification);

        LOGGER.info(
            "API Response -> {} {} returned {}",
            requestSpecification.getMethod(),
            requestSpecification.getURI(),
            response.getStatusCode()
        );
        LOGGER.debug("Response body -> {}", response.getBody().asPrettyString());

        return response;
    }

    private void logHeaders(Headers headers) {
        headers.asList().forEach(header -> {
            String value = "Authorization".equalsIgnoreCase(header.getName()) ? "***masked***" : header.getValue();
            LOGGER.debug("Header -> {}: {}", header.getName(), value);
        });
    }
}
