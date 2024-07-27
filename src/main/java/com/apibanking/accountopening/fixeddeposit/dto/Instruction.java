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


}
