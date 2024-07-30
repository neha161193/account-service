package com.apibanking.account.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnTransformer;

import com.apibanking.account.payment.dto.Status;
import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private LocalDateTime requestTimestamp;
    @NotNull
    private String fromAccountNo;
    @NotNull
    private String fromAccountBankName;
    @NotNull
    private String fromAccountBankIFSC;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String toAccountNo;
    @NotNull
    private String toAccountCustomerId;
    @NotNull
    private String remarks;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;
    @NotNull
    @JsonRawValue
    @Column(columnDefinition = "json")
    @ColumnTransformer(write = "?::jsonb")
    private String requestPayload;
    private String responsePayload;
    private LocalDateTime responseTimestamp;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }
    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }
    public String getFromAccountNo() {
        return fromAccountNo;
    }
    public void setFromAccountNo(String fromAccountNo) {
        this.fromAccountNo = fromAccountNo;
    }
    public String getFromAccountBankName() {
        return fromAccountBankName;
    }
    public void setFromAccountBankName(String fromAccountBankName) {
        this.fromAccountBankName = fromAccountBankName;
    }
    public String getFromAccountBankIFSC() {
        return fromAccountBankIFSC;
    }
    public void setFromAccountBankIFSC(String fromAccountBankIFSC) {
        this.fromAccountBankIFSC = fromAccountBankIFSC;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getToAccountNo() {
        return toAccountNo;
    }
    public void setToAccountNo(String toAccountNo) {
        this.toAccountNo = toAccountNo;
    }
    public String getToAccountCustomerId() {
        return toAccountCustomerId;
    }
    public void setToAccountCustomerId(String toAccountCustomerId) {
        this.toAccountCustomerId = toAccountCustomerId;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public String getRequestPayload() {
        return requestPayload;
    }
    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }
    public String getResponsePayload() {
        return responsePayload;
    }
    public void setResponsePayload(String responsePayload) {
        this.responsePayload = responsePayload;
    }
    public LocalDateTime getResponseTimestamp() {
        return responseTimestamp;
    }
    public void setResponseTimestamp(LocalDateTime responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }
    
}
