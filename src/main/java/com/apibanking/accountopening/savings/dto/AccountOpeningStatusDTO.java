package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;

public class AccountOpeningStatusDTO {
    @NotNull
    private AccountType type;
    private String customerId;
    private String accountNumber;
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
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
