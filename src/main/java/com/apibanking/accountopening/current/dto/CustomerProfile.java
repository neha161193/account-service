package com.apibanking.accountopening.current.dto;

import java.math.BigDecimal;

import com.apibanking.accountopening.savings.dto.FundSource;
import com.apibanking.accountopening.savings.dto.OccupationType;
import com.apibanking.accountopening.savings.dto.ResidenceType;
import com.apibanking.accountopening.savings.dto.SelfEmployed;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class CustomerProfile {
    @NotNull
    private OccupationType occupation;
    @Valid
    @NotNull
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
