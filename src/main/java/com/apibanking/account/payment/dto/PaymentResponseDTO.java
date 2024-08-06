package com.apibanking.account.payment.dto;

import jakarta.validation.constraints.NotNull;

public class PaymentResponseDTO {
    private String transactionReferenceNo;
    @NotNull
    private Status status;

    private String remarks;

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public String getTransactionReferenceNo() {
        return transactionReferenceNo;
    }
    public void setTransactionReferenceNo(String transactionReferenceNo) {
        this.transactionReferenceNo = transactionReferenceNo;
    }
    
}
