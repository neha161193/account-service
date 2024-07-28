package com.apibanking.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;

public class AccountDTO implements Serializable {
    @NotNull
    private String accountNo;

    private String applicationNo;
    @NotNull
    private String customerId;
    @NotNull
    private String panNo;
    @NotNull
    private String aadhaarNo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private BigDecimal accountBalance;
    private BigDecimal accountMinimumBalance;
    private String interestRate;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private LocalDate accountOpeningDate;
    private LocalDate accountClosingDate;
    @NotNull
    private String accountHolderName;
    private List<AccountAddressDTO> address;
    private AccountContactDTO contact;
    private AccountNomineeDTO nomineeDetail;
    private AccountDebitCardDetailDTO debitCardDetail;
  
    public List<AccountAddressDTO> getAddress() {
        return address;
    }
    public void setAddress(List<AccountAddressDTO> address) {
        this.address = address;
    }
    public AccountContactDTO getContact() {
        return contact;
    }
    public void setContact(AccountContactDTO contact) {
        this.contact = contact;
    }
    public AccountNomineeDTO getNomineeDetail() {
        return nomineeDetail;
    }
    public void setNomineeDetail(AccountNomineeDTO nomineeDetail) {
        this.nomineeDetail = nomineeDetail;
    }
    public AccountDebitCardDetailDTO getDebitCardDetail() {
        return debitCardDetail;
    }
    public void setDebitCardDetail(AccountDebitCardDetailDTO debitCardDetail) {
        this.debitCardDetail = debitCardDetail;
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
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }
    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }
    public BigDecimal getAccountMinimumBalance() {
        return accountMinimumBalance;
    }
    public void setAccountMinimumBalance(BigDecimal accountMinimumBalance) {
        this.accountMinimumBalance = accountMinimumBalance;
    }
    public String getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }
    public AccountStatus getStatus() {
        return status;
    }
    public void setStatus(AccountStatus status) {
        this.status = status;
    }
    public LocalDate getAccountOpeningDate() {
        return accountOpeningDate;
    }
    public void setAccountOpeningDate(LocalDate accountOpeningDate) {
        this.accountOpeningDate = accountOpeningDate;
    }
    public LocalDate getAccountClosingDate() {
        return accountClosingDate;
    }
    public void setAccountClosingDate(LocalDate accountClosingDate) {
        this.accountClosingDate = accountClosingDate;
    }
    public String getAccountHolderName() {
        return accountHolderName;
    }
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
 
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

}
