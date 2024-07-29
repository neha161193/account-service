package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;

public class AccountOpeningResponseDTO {
    private String customerId;
    @NotNull
    private String applicationNo;
    @NotNull
    private AccountStatus status;
    @NotNull
    private AccountType type;
    private String productCode;
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getApplicationNo() {
        return applicationNo;
    }
    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }
    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

}
