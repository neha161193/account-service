package com.apibanking.account.dto;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountContactDTO implements Serializable {
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
}
