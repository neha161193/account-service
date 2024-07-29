package com.apibanking.accountopening.fixeddeposit.dto;

import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.dto.Applicant;
import com.apibanking.accountopening.savings.dto.Nominee;
import com.apibanking.accountopening.savings.dto.OperatingType;
import com.apibanking.accountopening.savings.dto.PaymentDetail;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class FixedDepositAccountRequestDTO {
    @NotNull
    private AccountType type;
    @NotNull
    @Valid
    private Applicant applicant;
    @NotNull
    private OperatingType operatingInstruction;
    @NotNull
    private String customerId;
    @NotNull
    @Valid
    private PaymentDetail paymentDetail;
    @NotNull
    @Valid
    private Nominee nominee;
    private String productCode;
    @NotNull
    @Valid
    private Instruction instruction;

    public Instruction getInstruction() {
        return instruction;
    }
    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }
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
    
    public PaymentDetail getPaymentDetail() {
        return paymentDetail;
    }
    public void setPaymentDetail(PaymentDetail paymentDetail) {
        this.paymentDetail = paymentDetail;
    }
    
    public Nominee getNominee() {
        return nominee;
    }
    public void setNominee(Nominee nominee) {
        this.nominee = nominee;
    }
    
    public String getProductCode() {
        return productCode;
    }
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    
}
