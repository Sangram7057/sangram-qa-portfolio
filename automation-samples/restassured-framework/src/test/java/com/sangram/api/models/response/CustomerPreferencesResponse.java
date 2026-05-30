package com.sangram.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerPreferencesResponse {
    private String status;
    private PreferencesData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public PreferencesData getData() {
        return data;
    }

    public void setData(PreferencesData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PreferencesData {
        private String customerId;
        private Preferences preferences;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public Preferences getPreferences() {
            return preferences;
        }

        public void setPreferences(Preferences preferences) {
            this.preferences = preferences;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Preferences {
        private String language;
        private Notifications notifications;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Notifications getNotifications() {
            return notifications;
        }

        public void setNotifications(Notifications notifications) {
            this.notifications = notifications;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Notifications {
        private Boolean emailAlerts;
        private Boolean smsAlerts;
        private Boolean pushAlerts;

        public Boolean getEmailAlerts() {
            return emailAlerts;
        }

        public void setEmailAlerts(Boolean emailAlerts) {
            this.emailAlerts = emailAlerts;
        }

        public Boolean getSmsAlerts() {
            return smsAlerts;
        }

        public void setSmsAlerts(Boolean smsAlerts) {
            this.smsAlerts = smsAlerts;
        }

        public Boolean getPushAlerts() {
            return pushAlerts;
        }

        public void setPushAlerts(Boolean pushAlerts) {
            this.pushAlerts = pushAlerts;
        }
    }
}
