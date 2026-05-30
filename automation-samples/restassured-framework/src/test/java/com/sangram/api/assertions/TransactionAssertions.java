package com.sangram.api.assertions;

import com.sangram.api.models.response.TransactionsResponse;
import org.testng.Assert;

public final class TransactionAssertions {
    private TransactionAssertions() {
    }

    public static void assertTransactionFilterContract(TransactionsResponse response, String expectedDateRange) {
        Assert.assertEquals(response.getStatus(), "SUCCESS");
        Assert.assertNotNull(response.getData(), "Transaction response data should be present.");
        Assert.assertNotNull(response.getData().getFilters(), "Filter metadata should be present.");
        Assert.assertEquals(response.getData().getFilters().getDateRange(), expectedDateRange);
        Assert.assertNotNull(response.getData().getTransactions(), "Transactions list should be present.");
        Assert.assertFalse(response.getData().getTransactions().isEmpty(), "Transactions list should not be empty.");
        Assert.assertNotNull(response.getData().getTransactions().get(0).getTransactionId(), "Transaction ID should be present.");
        Assert.assertNotNull(response.getData().getTransactions().get(0).getType(), "Transaction type should be present.");
        Assert.assertNotNull(response.getData().getTransactions().get(0).getAmount(), "Transaction amount should be present.");
    }
}
