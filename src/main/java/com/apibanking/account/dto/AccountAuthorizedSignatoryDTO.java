package com.apibanking.account.dto;

import java.time.LocalDate;

import com.apibanking.accountopening.savings.dto.GenderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountAuthorizedSignatoryDTO {
    @NotNull
    private String applicantFirstName;
    private String applicantMiddleName;
    @NotNull
    private String applicantLastName;
    @NotNull
    private AccountAddressDTO address;
    @NotNull
    @NotBlank
    private String nationality;
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]")
    @NotNull
    public String panNo;
    @NotNull
    private GenderType gender;
    @NotNull
    private String customerId;
    @NotNull
    private LocalDate dateOfBirth;
    private String mobileNo;
    private String email;
    private boolean instaAlert;

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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public GenderType getGender() {
        return gender;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public boolean isInstaAlert() {
        return instaAlert;
    }

    public void setInstaAlert(boolean instaAlert) {
        this.instaAlert = instaAlert;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AccountAddressDTO getAddress() {
        return address;
    }

    public void setAddress(AccountAddressDTO address) {
        this.address = address;
    }
}
