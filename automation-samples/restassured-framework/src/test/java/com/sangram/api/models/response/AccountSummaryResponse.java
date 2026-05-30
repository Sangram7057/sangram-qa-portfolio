package com.sangram.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountSummaryResponse {
    private String status;
    private AccountSummaryData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AccountSummaryData getData() {
        return data;
    }

    public void setData(AccountSummaryData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccountSummaryData {
        private String customerId;
        private List<AccountRecord> accounts;

        public String getCustomerId() {
            return customerId;
        }

        public void setCustomerId(String customerId) {
            this.customerId = customerId;
        }

        public List<AccountRecord> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<AccountRecord> accounts) {
            this.accounts = accounts;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccountRecord {
        private String accountId;
        private String currency;
        private BigDecimal ledgerBalance;
        private BigDecimal availableBalance;
        private String accountType;

        public String getAccountId() {
            return accountId;
        }

        public void setAccountId(String accountId) {
            this.accountId = accountId;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public BigDecimal getLedgerBalance() {
            return ledgerBalance;
        }

        public void setLedgerBalance(BigDecimal ledgerBalance) {
            this.ledgerBalance = ledgerBalance;
        }

        public BigDecimal getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(BigDecimal availableBalance) {
            this.availableBalance = availableBalance;
        }

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }
    }
}
