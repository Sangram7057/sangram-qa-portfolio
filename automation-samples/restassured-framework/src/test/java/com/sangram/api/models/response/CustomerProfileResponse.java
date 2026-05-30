package com.sangram.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerProfileResponse {
    private String status;
    private CustomerProfileData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public CustomerProfileData getData() {
        return data;
    }

    public void setData(CustomerProfileData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CustomerProfileData {
        private String customerId;
        private Profile profile;
        private Preferences preferences;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public Preferences getPreferences() {
            return preferences;
        }

        public void setPreferences(Preferences preferences) {
            this.preferences = preferences;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Profile {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Preferences {
        private String language;
        private Boolean paperlessStatements;

        public String getLanguage() {
            return language;
        }

        public void setLanguage(String language) {
            this.language = language;
        }

        public Boolean getPaperlessStatements() {
            return paperlessStatements;
        }

        public void setPaperlessStatements(Boolean paperlessStatements) {
            this.paperlessStatements = paperlessStatements;
        }
    }
}
