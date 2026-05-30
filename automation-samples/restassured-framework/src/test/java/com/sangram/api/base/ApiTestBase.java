package com.sangram.api.base;

import com.sangram.api.clients.AccountsClient;
import com.sangram.api.clients.CustomersClient;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public abstract class ApiTestBase {
    protected AccountsClient accountsClient;
    protected CustomersClient customersClient;

    @BeforeClass
    public void setUpApiClients() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        accountsClient = new AccountsClient();
        customersClient = new CustomersClient();
    }
}
