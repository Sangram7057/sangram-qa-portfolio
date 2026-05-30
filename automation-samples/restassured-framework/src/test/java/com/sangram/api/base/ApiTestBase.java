package com.sangram.api.base;

import com.sangram.api.clients.AccountsClient;
import com.sangram.api.clients.CustomersClient;
import io.restassured.RestAssured;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

public abstract class ApiTestBase {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected AccountsClient accountsClient;
    protected CustomersClient customersClient;

    @BeforeClass
    public void setUpApiClients() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        accountsClient = new AccountsClient();
        customersClient = new CustomersClient();
        logger.info("Initialized API clients for {}", getClass().getSimpleName());
    }
}
