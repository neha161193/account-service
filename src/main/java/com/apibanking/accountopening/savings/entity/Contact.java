package com.apibanking.accountopening.savings.entity;

import java.io.Serializable;

import com.apibanking.accountopening.current.dto.Frequency;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private boolean emailAlert;
    @Enumerated(EnumType.STRING)
    private Frequency emailStatementFrequency;
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
    @JoinColumn(name = "accountOpeningRequest_id")
    private AccountOpeningRequest accountOpeningRequest;

    public boolean isEmailAlert() {
        return emailAlert;
    }

    public void setEmailAlert(boolean emailAlert) {
        this.emailAlert = emailAlert;
    }

    public Frequency getEmailStatementFrequency() {
        return emailStatementFrequency;
    }

    public void setEmailStatementFrequency(Frequency emailStatementFrequency) {
        this.emailStatementFrequency = emailStatementFrequency;
    }

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

    public AccountOpeningRequest getAccountOpeningRequest() {
        return accountOpeningRequest;
    }

    public void setAccountOpeningRequest(AccountOpeningRequest accountOpeningRequest) {
        this.accountOpeningRequest = accountOpeningRequest;
    }
}
