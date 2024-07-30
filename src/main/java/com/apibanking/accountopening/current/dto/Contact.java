package com.apibanking.accountopening.current.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
public class Contact {
    @NotNull
    @NotBlank
    private String telephoneNumber1;
    @NotNull
    @NotBlank
    private String telephoneNumber2;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @Pattern(regexp = "^\\d{1,14}$")
    @NotNull
    private String mobileNumber;
    private boolean emailAlert;
    private Frequency emailStatementFrequency;
    public String getTelephoneNumber1() {
        return telephoneNumber1;
    }
    public void setTelephoneNumber1(String telephoneNumber1) {
        this.telephoneNumber1 = telephoneNumber1;
    }
    public String getTelephoneNumber2() {
        return telephoneNumber2;
    }
    public void setTelephoneNumber2(String telephoneNumber2) {
        this.telephoneNumber2 = telephoneNumber2;
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
    
}
