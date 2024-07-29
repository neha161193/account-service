package com.apibanking.account.payment.dto;

import jakarta.validation.constraints.NotNull;

public class ToAccountDTO {
    @NotNull
    private String accountNo;
    @NotNull
    private String customerId;
    
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    
}
