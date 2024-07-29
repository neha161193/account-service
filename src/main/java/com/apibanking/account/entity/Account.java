package com.apibanking.account.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String accountNo;

    @NotNull
    private String applicationNo;
    @NotNull
    private String customerId;
    @NotNull
    private String panNo;
    private String aadhaarNo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType type;
    @NotNull
    private BigDecimal accountBalance;
    private BigDecimal accountMinimumBalance;
    @NotNull
    private String interestRate;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @NotNull
    private LocalDate accountOpeningDate;
    private LocalDate accountClosingDate;
    @NotNull
    private String accountHolderName;
    @JsonbTransient
    @NotNull
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountAddress> address  = new ArrayList<AccountAddress>();

    @JsonbTransient
    @NotNull
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<AccountAuthorizedSignatory> accountAuthorizedSignatory  = new ArrayList<AccountAuthorizedSignatory>();

    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AccountContact contact;
    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccountNominee nomineeDetail;
    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccountDebitCardDetail debitCardDetail;
  
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
 
    public AccountNominee getNomineeDetail() {
        return nomineeDetail;
    }
    public void setNomineeDetail(AccountNominee nomineeDetail) {
        this.nomineeDetail = nomineeDetail;
    }
    public AccountDebitCardDetail getDebitCardDetail() {
        return debitCardDetail;
    }
    public void setDebitCardDetail(AccountDebitCardDetail debitCardDetail) {
        this.debitCardDetail = debitCardDetail;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public AccountContact getContact() {
        return contact;
    }
    public void setContact(AccountContact contact) {
        this.contact = contact;
    }
    public List<AccountAddress> getAddress() {
        return address;
    }
    public void setAddress(List<AccountAddress> address) {
        this.address = address;
    }
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public void addAddress(AccountAddress address) {
        this.address.add(address);
        address.setAccount(this);
    }

    public void removeAddress(AccountAddress address) {
        this.address.remove(address);
        address.setAccount(null);
    }
    public List<AccountAuthorizedSignatory> getAccountAuthorizedSignatory() {
        return accountAuthorizedSignatory;
    }
    public void setAccountAuthorizedSignatory(List<AccountAuthorizedSignatory> accountAuthorizedSignatory) {
        this.accountAuthorizedSignatory = accountAuthorizedSignatory;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }

}
