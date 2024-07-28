package com.apibanking.account.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class AccountNomineeDTO {
    @NotNull
    private boolean optForNominee;
    private String name;
    @NotNull
    private AccountAddressDTO address;
    private String residenceTelephone;
    private String relationWithApplicant;
    private LocalDate dateOfBirth;
    private String mobileNumber;
   
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
    public AccountAddressDTO getAddress() {
        return address;
    }
    public void setAddress(AccountAddressDTO address) {
        this.address = address;
    }
}
