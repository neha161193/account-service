package com.apibanking.accountopening.savings.dto;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class CustomerProfile {
    @NotNull
    private OccupationType occupation;
    @Valid
    private Salaried salaried;
    @Valid
    private SelfEmployed selfEmployed;
    @NotNull
    private FundSource sourceOfFunds;
    @NotNull
    private BigDecimal grossAnnualIncome;
    @NotNull
    private ResidenceType residenceType;
    public OccupationType getOccupation() {
        return occupation;
    }
    public void setOccupation(OccupationType occupation) {
        this.occupation = occupation;
    }
    public Salaried getSalaried() {
        return salaried;
    }
    public void setSalaried(Salaried salaried) {
        this.salaried = salaried;
    }
    public SelfEmployed getSelfEmployed() {
        return selfEmployed;
    }
    public void setSelfEmployed(SelfEmployed selfEmployed) {
        this.selfEmployed = selfEmployed;
    }
    public FundSource getSourceOfFunds() {
        return sourceOfFunds;
    }
    public void setSourceOfFunds(FundSource sourceOfFunds) {
        this.sourceOfFunds = sourceOfFunds;
    }
    public BigDecimal getGrossAnnualIncome() {
        return grossAnnualIncome;
    }
    public void setGrossAnnualIncome(BigDecimal grossAnnualIncome) {
        this.grossAnnualIncome = grossAnnualIncome;
    }
    public ResidenceType getResidenceType() {
        return residenceType;
    }
    public void setResidenceType(ResidenceType residenceType) {
        this.residenceType = residenceType;
    }

}
