package com.sangram.api.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionsResponse {
    private String status;
    private TransactionsData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TransactionsData getData() {
        return data;
    }

    public void setData(TransactionsData data) {
        this.data = data;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionsData {
        private Filters filters;
        private List<TransactionRecord> transactions;

        public Filters getFilters() {
            return filters;
        }

        public void setFilters(Filters filters) {
            this.filters = filters;
        }

        public List<TransactionRecord> getTransactions() {
            return transactions;
        }

        public void setTransactions(List<TransactionRecord> transactions) {
            this.transactions = transactions;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Filters {
        private String dateRange;
        private String transactionType;
        private Integer page;
        private Integer pageSize;

        public String getDateRange() {
            return dateRange;
        }

        public void setDateRange(String dateRange) {
            this.dateRange = dateRange;
        }

        public String getTransactionType() {
            return transactionType;
        }

        public void setTransactionType(String transactionType) {
            this.transactionType = transactionType;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TransactionRecord {
        private String transactionId;
        private String type;
        private BigDecimal amount;
        private String currency;
        private String bookingDate;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getBookingDate() {
            return bookingDate;
        }

        public void setBookingDate(String bookingDate) {
            this.bookingDate = bookingDate;
        }
    }
}
