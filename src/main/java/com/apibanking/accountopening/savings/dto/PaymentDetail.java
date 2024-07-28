package com.apibanking.accountopening.savings.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public class PaymentDetail {
    @NotNull
    private PaymentMode paymentMode;
    @NotNull
    private BigDecimal amount;
    @Pattern(regexp = "^\\d{6,10}$")
    private String chequeNo;
    private LocalDate chequeDate;
    @Pattern(regexp = "^[a-zA-Z0-9\\s.&'-]*$")
    private String bankName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,'&()-]*$")
    private String bankBranch;
    public PaymentMode getPaymentMode() {
        return paymentMode;
    }
    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getChequeNo() {
        return chequeNo;
    }
    public void setChequeNo(String chequeNo) {
        this.chequeNo = chequeNo;
    }
    public LocalDate getChequeDate() {
        return chequeDate;
    }
    public void setChequeDate(LocalDate chequeDate) {
        this.chequeDate = chequeDate;
    }
    public String getBankName() {
        return bankName;
    }
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public String getBankBranch() {
        return bankBranch;
    }
    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }
    
}
