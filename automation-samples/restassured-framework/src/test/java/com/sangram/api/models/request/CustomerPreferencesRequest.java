package com.sangram.api.models.request;

public class CustomerPreferencesRequest {
    private String language;
    private NotificationSettings notifications;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public NotificationSettings getNotifications() {
        return notifications;
    }

    public void setNotifications(NotificationSettings notifications) {
        this.notifications = notifications;
    }

    public static class NotificationSettings {
        private boolean emailAlerts;
        private boolean smsAlerts;
        private boolean pushAlerts;

        public boolean isEmailAlerts() {
            return emailAlerts;
        }

        public void setEmailAlerts(boolean emailAlerts) {
            this.emailAlerts = emailAlerts;
        }

        public boolean isSmsAlerts() {
            return smsAlerts;
        }

        public void setSmsAlerts(boolean smsAlerts) {
            this.smsAlerts = smsAlerts;
        }

        public boolean isPushAlerts() {
            return pushAlerts;
        }

        public void setPushAlerts(boolean pushAlerts) {
            this.pushAlerts = pushAlerts;
        }
    }
}
