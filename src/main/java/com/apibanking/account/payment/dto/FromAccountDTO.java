package com.apibanking.account.payment.dto;

import jakarta.validation.constraints.NotNull;

public class FromAccountDTO {
    @NotNull
    private String accountHolderName;
    @NotNull
    private String accountNo;
    @NotNull
    private String bankName;
    @NotNull
    private String bankBranch;
    @NotNull
    private String ifscCode;

    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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
    public String getIfscCode() {
        return ifscCode;
    }
    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    
}
