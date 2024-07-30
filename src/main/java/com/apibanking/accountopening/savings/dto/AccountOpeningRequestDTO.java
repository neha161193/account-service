package com.apibanking.accountopening.savings.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountOpeningRequestDTO {
    @NotNull
    private AccountType type;
    @NotNull
    @Valid
    private Applicant applicant;
    
    @NotNull
    @NotBlank
    private String nationality;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]")
    @NotNull
    public String panNo;

    @Pattern(regexp = "\\d{12}")
    @NotNull
    private String aadhaarNo;
    @NotNull
    private boolean consentToLinkAadhaarWithAccount;
    @NotNull
    private LocalDate dateOfBirth;
    @NotNull
    private GenderType gender;
    @NotNull
    private String motherMaidenName;
    @NotNull
    private OperatingType operatingInstruction;
    private String customerId;
    @NotNull
    @Valid
    private Address[] address;
    @NotNull
    @Valid
    private Contact contact;
    @NotNull
    @Valid
    private CustomerProfile customerProfile;
    @Valid
    private Introducer introducer;
    @NotNull
    @Valid
    private PaymentDetail paymentDetail;
    @NotNull
    @Valid
    private DebitCardDetail debitCardDetail;
    @NotNull
    @Valid
    private Nominee nominee;
    @NotNull
    private BigDecimal requiredAverageBalance;
    private String productCode;

    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    public Applicant getApplicant() {
        return applicant;
    }
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
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
    public String getAadhaarNo() {
        return aadhaarNo;
    }
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }
    public boolean isConsentToLinkAadhaarWithAccount() {
        return consentToLinkAadhaarWithAccount;
    }
    public void setConsentToLinkAadhaarWithAccount(boolean consentToLinkAadhaarWithAccount) {
        this.consentToLinkAadhaarWithAccount = consentToLinkAadhaarWithAccount;
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
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public Address[] getAddress() {
        return address;
    }
    public void setAddress(Address[] address) {
        this.address = address;
    }
    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }
    public CustomerProfile getCustomerProfile() {
        return customerProfile;
    }
    public void setCustomerProfile(CustomerProfile customerProfile) {
        this.customerProfile = customerProfile;
    }
    public Introducer getIntroducer() {
        return introducer;
    }
    public void setIntroducer(Introducer introducer) {
        this.introducer = introducer;
    }
    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }
    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
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
    public BigDecimal getRequiredAverageBalance() {
        return requiredAverageBalance;
    }
    public void setRequiredAverageBalance(BigDecimal requiredAverageBalance) {
        this.requiredAverageBalance = requiredAverageBalance;
    }
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}
