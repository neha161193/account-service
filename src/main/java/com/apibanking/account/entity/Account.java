package com.apibanking.account.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.entity.Address;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Account {
    @NotNull
    private String accountNumber;

    @NotNull
    private String applicationNo;
    @NotNull
    private String customerId;
    @NotNull
    private String panNo;
    @NotNull
    private String aadhaarNo;
    @NotNull
    private AccountType accountType;
    @NotNull
    private BigDecimal accountBalance;
    private BigDecimal accountMinimumBalance;
    @NotNull
    private String interestRate;
    @NotNull
    private AccountStatus status;
    @NotNull
    private LocalDate accountOpeningDate;
    private LocalDate accountClosingDate;
    @NotNull
    private String accountHolderName;
    @NotNull
    private List<Address> accountHolderAddress;
    @NotNull
    private Contact accountHolderContact;
    @NotNull
    private Nominee nomineeDetail;
    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private DebitCardDetail debitCardDetail;

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public String getApplicationNo() {
        return applicationNo;
    }
    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getPanNo() {
        return panNo;
    }
    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }
    public String getAadhaarNo() {
        return aadhaarNo;
    }
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }
    public String getAccountType() {
        return accountType;
    }
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    public String getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(String accountBalance) {
        this.accountBalance = accountBalance;
    }
    public String getAccountMinimumBalance() {
        return accountMinimumBalance;
    }
    public void setAccountMinimumBalance(String accountMinimumBalance) {
        this.accountMinimumBalance = accountMinimumBalance;
    }
    public String getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAccountOpeningDate() {
        return accountOpeningDate;
    }
    public void setAccountOpeningDate(String accountOpeningDate) {
        this.accountOpeningDate = accountOpeningDate;
    }
    public String getAccountClosingDate() {
        return accountClosingDate;
    }
    public void setAccountClosingDate(String accountClosingDate) {
        this.accountClosingDate = accountClosingDate;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
    public String getAccountHolderAddress() {
        return accountHolderAddress;
    }
    public void setAccountHolderAddress(String accountHolderAddress) {
        this.accountHolderAddress = accountHolderAddress;
    }
    public String getAccountHolderContact() {
        return accountHolderContact;
    }
    public void setAccountHolderContact(String accountHolderContact) {
        this.accountHolderContact = accountHolderContact;
    }
    public String getNomineeDetail() {
        return nomineeDetail;
    }
    public void setNomineeDetail(String nomineeDetail) {
        this.nomineeDetail = nomineeDetail;
    }

    

}
