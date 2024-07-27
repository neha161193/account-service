package com.apibanking.account.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   
    @NotNull
    @NotBlank
    private String residenceTelephone;
    @NotNull
    @NotBlank
    private String officeTelephone;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @Pattern(regexp = "^\\d{1,14}$")
    @NotNull
    private String mobileNumber;
    private String mobileNumberServiceProvider;
    private boolean instaAlert;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "savingAccountRequest_id")
    private SavingAccountRequest savingAccountRequest;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResidenceTelephone() {
        return residenceTelephone;
    }

    public void setResidenceTelephone(String residenceTelephone) {
        this.residenceTelephone = residenceTelephone;
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getMobileNumberServiceProvider() {
        return mobileNumberServiceProvider;
    }

    public void setMobileNumberServiceProvider(String mobileNumberServiceProvider) {
        this.mobileNumberServiceProvider = mobileNumberServiceProvider;
    }

    public boolean isInstaAlert() {
        return instaAlert;
    }

    public void setInstaAlert(boolean instaAlert) {
        this.instaAlert = instaAlert;
    }

    public SavingAccountRequest getSavingAccountRequest() {
        return savingAccountRequest;
    }

    public void setSavingAccountRequest(SavingAccountRequest savingAccountRequest) {
        this.savingAccountRequest = savingAccountRequest;
    }
}
