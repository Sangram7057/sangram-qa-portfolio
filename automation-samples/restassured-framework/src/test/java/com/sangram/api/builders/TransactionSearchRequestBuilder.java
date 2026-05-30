package com.sangram.api.builders;

import com.sangram.api.models.request.TransactionSearchRequest;

public class TransactionSearchRequestBuilder {
    private String dateRange = "last-30-days";
    private String transactionType = "credit";
    private int page = 1;
    private int pageSize = 20;

    public TransactionSearchRequestBuilder dateRange(String dateRange) {
        this.dateRange = dateRange;
        return this;
    }

    public TransactionSearchRequestBuilder transactionType(String transactionType) {
        this.transactionType = transactionType;
        return this;
    }

    public TransactionSearchRequestBuilder page(int page) {
        this.page = page;
        return this;
    }

    public TransactionSearchRequestBuilder pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public TransactionSearchRequest build() {
        TransactionSearchRequest request = new TransactionSearchRequest();
        request.setDateRange(dateRange);
        request.setTransactionType(transactionType);
        request.setPage(page);
        request.setPageSize(pageSize);
        return request;
    }
}
