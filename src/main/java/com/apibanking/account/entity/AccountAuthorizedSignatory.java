package com.apibanking.account.entity;

import java.time.LocalDate;

import com.apibanking.accountopening.savings.dto.GenderType;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name="account_authorizedsignatory")
public class AccountAuthorizedSignatory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String applicantFirstName;
    private String applicantMiddleName;
    @NotNull
    private String applicantLastName;
    @OneToOne(mappedBy = "accountAuthorizedSignatory", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JsonbTransient
    @NotNull
    private AccountAddress address;
    @NotNull
    @NotBlank
    private String nationality;
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]")
    @NotNull
    public String panNo;
    @NotNull
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    @NotNull
    private String customerId;
    @NotNull
    private LocalDate dateOfBirth;
    private String mobileNo;
    private String email;
    private boolean instaAlert;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public AccountAddress getAddress() {
        return address;
    }

    public void setAddress(AccountAddress address) {
        this.address = address;
    }
}
