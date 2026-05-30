package com.sangram.api.assertions;

import com.sangram.api.models.response.AccountDetailsResponse;
import com.sangram.api.models.response.AccountSummaryResponse;
import org.testng.Assert;

public final class AccountAssertions {
    private AccountAssertions() {
    }

    public static void assertSummaryContract(AccountSummaryResponse response) {
        Assert.assertEquals(response.getStatus(), "SUCCESS");
        Assert.assertNotNull(response.getData(), "Summary data should be present.");
        Assert.assertNotNull(response.getData().getCustomerId(), "Customer ID should be present.");
        Assert.assertNotNull(response.getData().getAccounts(), "Accounts list should be present.");
        Assert.assertFalse(response.getData().getAccounts().isEmpty(), "Accounts list should not be empty.");
    }

    public static void assertPrimaryBalanceWidgets(AccountSummaryResponse response) {
        AccountSummaryResponse.AccountRecord primaryAccount = response.getData().getAccounts().get(0);
        Assert.assertNotNull(primaryAccount.getAccountId(), "Account ID should be present.");
        Assert.assertNotNull(primaryAccount.getCurrency(), "Currency should be present.");
        Assert.assertNotNull(primaryAccount.getLedgerBalance(), "Ledger balance should be present.");
        Assert.assertNotNull(primaryAccount.getAvailableBalance(), "Available balance should be present.");
        Assert.assertNotNull(primaryAccount.getAccountType(), "Account type should be present.");
    }

    public static void assertAccountDetails(AccountDetailsResponse response, String expectedAccountId) {
        Assert.assertEquals(response.getStatus(), "SUCCESS");
        Assert.assertNotNull(response.getData(), "Account details data should be present.");
        Assert.assertEquals(response.getData().getAccountId(), expectedAccountId);
        Assert.assertNotNull(response.getData().getOwner(), "Owner information should be present.");
        Assert.assertNotNull(response.getData().getOwner().getCustomerId(), "Owner customer ID should be present.");
        Assert.assertNotNull(response.getData().getCurrency(), "Currency should be present.");
        Assert.assertNotNull(response.getData().getAvailableBalance(), "Available balance should be present.");
    }
}
