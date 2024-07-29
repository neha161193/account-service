package com.apibanking.accountopening.current.dto;

import com.apibanking.accountopening.savings.dto.OperatingType;
import com.apibanking.accountopening.savings.dto.CustomerProfile;
import com.apibanking.accountopening.savings.dto.DebitCardDetail;
import com.apibanking.accountopening.savings.dto.Introducer;
import com.apibanking.accountopening.savings.dto.PaymentDetail;
import com.apibanking.accountopening.savings.dto.Nominee;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.dto.Address;
import com.apibanking.accountopening.savings.dto.Applicant;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CurrentAccountRequestDTO {
    @NotNull
    private AccountType type;
    @NotNull
    @Valid
    private Applicant applicant;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]")
    @NotNull
    public String panNo;
    public String aadhaarNo;
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
    @NotNull
    private OperatingType operatingInstruction;
    @NotNull
    @Valid
    private PaymentDetail paymentDetail;
    @NotNull
    @Valid
    private List<AuthorizedSignatoryDetail> authorizedSignatoryDetail  = new ArrayList<>();
    @NotNull
    @Valid
    private DebitCardDetail debitCardDetail;
    private Introducer introducer;
    @NotNull
    @Valid
    private Nominee nominee;
    @NotNull
    private BigDecimal requiredAverageBalance;

    public String getAadhaarNo() {
        return aadhaarNo;
    }
    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }
    public BigDecimal getRequiredAverageBalance() {
        return requiredAverageBalance;
    }
    public void setRequiredAverageBalance(BigDecimal requiredAverageBalance) {
        this.requiredAverageBalance = requiredAverageBalance;
    }
    public AccountType getType() {
        return type;
    }
    public void setType(AccountType type) {
        this.type = type;
    }
    public String getPanNo() {
        return panNo;
    }
    public void setPanNo(String panNo) {
        this.panNo = panNo;
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
    public OperatingType getOperatingInstruction() {
        return operatingInstruction;
    }
    public void setOperatingInstruction(OperatingType operatingInstruction) {
        this.operatingInstruction = operatingInstruction;
    }
    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }
    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }
    public Introducer getIntroducer() {
        return introducer;
    }
    public void setIntroducer(Introducer introducer) {
        this.introducer = introducer;
    }
    public Nominee getNominee() {
        return nominee;
    }
    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }
    public Applicant getApplicant() {
        return applicant;
    }
    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }
    public List<AuthorizedSignatoryDetail> getAuthorizedSignatoryDetail() {
        return authorizedSignatoryDetail;
    }
    public void setAuthorizedSignatoryDetail(List<AuthorizedSignatoryDetail> authorizedSignatoryDetail) {
        this.authorizedSignatoryDetail = authorizedSignatoryDetail;
    }
    public DebitCardDetail getDebitCardDetail() {
        return debitCardDetail;
    }
    public void setDebitCardDetail(DebitCardDetail debitCardDetail) {
        this.debitCardDetail = debitCardDetail;
    }
    
    
}
