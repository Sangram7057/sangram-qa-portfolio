package com.sangram.api.data;

import com.sangram.api.config.ApiConfig;

public final class ApiTestData {
    public static final String PRIMARY_ACCOUNT_ID = ApiConfig.defaultAccountId();
    public static final String PRIMARY_CUSTOMER_ID = ApiConfig.defaultCustomerId();
    public static final String LAST_30_DAYS = "last-30-days";
    public static final String CREDIT = "credit";
    public static final String INVALID_ACCOUNT_ID = "ACC-INVALID-404";

    private ApiTestData() {
    }
}
