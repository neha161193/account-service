package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;

public class AccountOpeningStatusDTO {
    @NotNull
    private AccountType type;
    private String customerId;
    private String accountNo;
    @NotNull
    private AccountStatus status;

    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
}
