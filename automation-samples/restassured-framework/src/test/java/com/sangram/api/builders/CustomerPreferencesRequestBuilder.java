package com.sangram.api.builders;

import com.sangram.api.models.request.CustomerPreferencesRequest;

public class CustomerPreferencesRequestBuilder {
    private String language = "en";
    private boolean emailAlerts = true;
    private boolean smsAlerts = true;
    private boolean pushAlerts = false;

    public CustomerPreferencesRequestBuilder language(String language) {
        this.language = language;
        return this;
    }

    public CustomerPreferencesRequestBuilder emailAlerts(boolean emailAlerts) {
        this.emailAlerts = emailAlerts;
        return this;
    }

    public CustomerPreferencesRequestBuilder smsAlerts(boolean smsAlerts) {
        this.smsAlerts = smsAlerts;
        return this;
    }

    public CustomerPreferencesRequestBuilder pushAlerts(boolean pushAlerts) {
        this.pushAlerts = pushAlerts;
        return this;
    }

    public CustomerPreferencesRequest build() {
        CustomerPreferencesRequest request = new CustomerPreferencesRequest();
        request.setLanguage(language);

        CustomerPreferencesRequest.NotificationSettings notifications =
            new CustomerPreferencesRequest.NotificationSettings();
        notifications.setEmailAlerts(emailAlerts);
        notifications.setSmsAlerts(smsAlerts);
        notifications.setPushAlerts(pushAlerts);

        request.setNotifications(notifications);
        return request;
    }
}
