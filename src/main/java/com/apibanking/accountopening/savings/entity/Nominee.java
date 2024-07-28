package com.apibanking.accountopening.savings.entity;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Nominee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    @NotNull
    private boolean optForNominee;
    private String name;
    @NotNull
    @OneToOne(mappedBy = "nominee", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Address address;
    private String residenceTelephone;
    private String relationWithApplicant;
    private LocalDate dateOfBirth;
    private String mobileNumber;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountOpeningRequest_id")
    private AccountOpeningRequest accountOpeningRequest;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public boolean isOptForNominee() {
        return optForNominee;
    }
    public void setOptForNominee(boolean optForNominee) {
        this.optForNominee = optForNominee;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public String getResidenceTelephone() {
        return residenceTelephone;
    }
    public void setResidenceTelephone(String residenceTelephone) {
        this.residenceTelephone = residenceTelephone;
    }
    public String getRelationWithApplicant() {
        return relationWithApplicant;
    }
    public void setRelationWithApplicant(String relationWithApplicant) {
        this.relationWithApplicant = relationWithApplicant;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public AccountOpeningRequest getAccountOpeningRequest() {
        return accountOpeningRequest;
    }
    public void setAccountOpeningRequest(AccountOpeningRequest accountOpeningRequest) {
        this.accountOpeningRequest = accountOpeningRequest;
    }

}
