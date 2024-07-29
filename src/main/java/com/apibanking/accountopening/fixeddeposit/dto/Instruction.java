package com.apibanking.accountopening.fixeddeposit.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;

public class Instruction {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private Tenure tenure;
    @NotNull
    private String rateOfInterest;
    @NotNull
    private InterestPaymentType interestPayment;
    @NotNull
    private MaturityInstruction maturityInstruction;
    @NotNull
    private String debitAccountNumber;
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public Tenure getTenure() {
        return tenure;
    }
    public void setTenure(Tenure tenure) {
        this.tenure = tenure;
    }
    public String getRateOfInterest() {
        return rateOfInterest;
    }
    public void setRateOfInterest(String rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }
    public InterestPaymentType getInterestPayment() {
        return interestPayment;
    }
    public void setInterestPayment(InterestPaymentType interestPayment) {
        this.interestPayment = interestPayment;
    }
    public MaturityInstruction getMaturityInstruction() {
        return maturityInstruction;
    }
    public void setMaturityInstruction(MaturityInstruction maturityInstruction) {
        this.maturityInstruction = maturityInstruction;
    }
    public String getDebitAccountNumber() {
        return debitAccountNumber;
    }
    public void setDebitAccountNumber(String debitAccountNumber) {
        this.debitAccountNumber = debitAccountNumber;
    }


}
