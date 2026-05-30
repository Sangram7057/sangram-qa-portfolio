package com.sangram.api.assertions;

import com.sangram.api.models.response.CustomerPreferencesResponse;
import com.sangram.api.models.response.CustomerProfileResponse;
import org.testng.Assert;

public final class CustomerAssertions {
    private CustomerAssertions() {
    }

    public static void assertCustomerProfile(CustomerProfileResponse response, String expectedCustomerId) {
        Assert.assertEquals(response.getStatus(), "SUCCESS");
        Assert.assertNotNull(response.getData(), "Customer profile data should be present.");
        Assert.assertEquals(response.getData().getCustomerId(), expectedCustomerId);
        Assert.assertNotNull(response.getData().getProfile(), "Customer profile details should be present.");
        Assert.assertNotNull(response.getData().getProfile().getEmail(), "Email should be present.");
        Assert.assertNotNull(response.getData().getProfile().getPhone(), "Phone should be present.");
        Assert.assertNotNull(response.getData().getPreferences(), "Customer preferences should be present.");
        Assert.assertNotNull(response.getData().getPreferences().getLanguage(), "Language should be present.");
    }

    public static void assertCustomerPreferences(CustomerPreferencesResponse response, String expectedCustomerId, String expectedLanguage) {
        Assert.assertEquals(response.getStatus(), "SUCCESS");
        Assert.assertNotNull(response.getData(), "Customer preferences data should be present.");
        Assert.assertEquals(response.getData().getCustomerId(), expectedCustomerId);
        Assert.assertNotNull(response.getData().getPreferences(), "Preferences should be present.");
        Assert.assertEquals(response.getData().getPreferences().getLanguage(), expectedLanguage);
        Assert.assertNotNull(response.getData().getPreferences().getNotifications(), "Notification preferences should be present.");
    }
}
