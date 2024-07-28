package com.apibanking.accountopening.savings.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnTransformer;

import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.dto.GenderType;
import com.apibanking.accountopening.savings.dto.OperatingType;
import com.fasterxml.jackson.annotation.JsonRawValue;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
import jakarta.validation.constraints.NotNull;

/**
 * Store saving account opening request and get application number in return
 * inquire your account opening request with application no
 * update your contact, using account opening PATCH api, for account status
 * ACTIVE
 * webhook to update the account status as ACTIVE from PendingActivation and put
 * record into master account
 */
@Entity
@Table(name = "SavingAccountRequest", indexes = {
    @Index(name = "idx_applicationNo", columnList = "applicationNo")
})
public class SavingAccountRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String applicationNo;
    @NotNull
    private String applicantFirstName;
    private String applicantMiddleName;
    @NotNull
    private String applicantLastName;
    @NotNull
    private String panNo;
    @NotNull
    private String aadhaarNo;
    private String customerId;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private String accountNo;
    @NotNull
    private LocalDateTime requestTimestamp = LocalDateTime.now();
    private LocalDateTime responseTimestamp;
    @NotNull
    @JsonRawValue
    @Column(columnDefinition = "json")
    @ColumnTransformer(write = "?::jsonb")
    private String requestPayload;
    @JsonRawValue
    @Column(columnDefinition = "json")
    @ColumnTransformer(write = "?::jsonb")
    private String responsePayload;
    private LocalDate dateOfBirth;
    @NotNull
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @NotNull
    private String motherMaidenName;
    @NotNull
    @Enumerated(EnumType.STRING)
    private OperatingType operatingInstruction;
    @JsonbTransient
    @NotNull
    @OneToMany(mappedBy = "savingAccountRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Address> address;
    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "savingAccountRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Contact contact;
    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "savingAccountRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private DebitCardDetail debitCardDetail;
    @JsonbTransient
    @NotNull
    @OneToOne(mappedBy = "savingAccountRequest", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Nominee nominee;
    @NotNull
    private BigDecimal requiredAverageBalance;

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }

    public String getApplicantFirstName() {
        return applicantFirstName;
    }

    public void setApplicantFirstName(String applicantFirstName) {
        this.applicantFirstName = applicantFirstName;
    }

    public String getApplicantMiddleName() {
        return applicantMiddleName;
    }

    public void setApplicantMiddleName(String applicantMiddleName) {
        this.applicantMiddleName = applicantMiddleName;
    }

    public String getApplicantLastName() {
        return applicantLastName;
    }

    public void setApplicantLastName(String applicantLastName) {
        this.applicantLastName = applicantLastName;
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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public LocalDateTime getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(LocalDateTime requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public LocalDateTime getResponseTimestamp() {
        return responseTimestamp;
    }

    public void setResponseTimestamp(LocalDateTime responseTimestamp) {
        this.responseTimestamp = responseTimestamp;
    }

    public String getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(String requestPayload) {
        this.requestPayload = requestPayload;
    }

    public String getResponsePayload() {
        return responsePayload;
    }

    public void setResponsePayload(String responsePayload) {
        this.responsePayload = responsePayload;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getMotherMaidenName() {
        return motherMaidenName;
    }

    public void setMotherMaidenName(String motherMaidenName) {
        this.motherMaidenName = motherMaidenName;
    }

    public OperatingType getOperatingInstruction() {
        return operatingInstruction;
    }

    public void setOperatingInstruction(OperatingType operatingInstruction) {
        this.operatingInstruction = operatingInstruction;
    }

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public BigDecimal getRequiredAverageBalance() {
        return requiredAverageBalance;
    }

    public void setRequiredAverageBalance(BigDecimal requiredAverageBalance) {
        this.requiredAverageBalance = requiredAverageBalance;
    }

    public DebitCardDetail getDebitCardDetail() {
        return debitCardDetail;
    }

    public void setDebitCardDetail(DebitCardDetail debitCardDetail) {
        this.debitCardDetail = debitCardDetail;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    
}