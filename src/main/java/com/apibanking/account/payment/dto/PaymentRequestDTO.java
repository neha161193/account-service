package com.apibanking.account.payment.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class PaymentRequestDTO {
    @NotNull
    private FromAccountDTO fromAccount;
    @NotNull
    private ToAccountDTO toAccount;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String remarks;
    
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public FromAccountDTO getFromAccount() {
        return fromAccount;
    }
    public void setFromAccount(FromAccountDTO fromAccount) {
        this.fromAccount = fromAccount;
    }
    public ToAccountDTO getToAccount() {
        return toAccount;
    }
    public void setToAccount(ToAccountDTO toAccount) {
        this.toAccount = toAccount;
    }

}
