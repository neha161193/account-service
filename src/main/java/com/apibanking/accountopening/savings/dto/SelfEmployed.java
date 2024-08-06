package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;

public class SelfEmployed {
    @NotNull
    private int selfEmployedSinceInYears;
    @NotNull
    private int selfEmployedSinceInMonths;
    @NotNull
    private BusinessType businessType;
    @NotNull
    private String dateOfIncorporation;
    @NotNull
    private String annualTurnOverInLacs;
    @NotNull
    private FirmType firm;
    private Professional profession;
    public int getSelfEmployedSinceInYears() {
        return selfEmployedSinceInYears;
    }
    public void setSelfEmployedSinceInYears(int selfEmployedSinceInYears) {
        this.selfEmployedSinceInYears = selfEmployedSinceInYears;
    }
    public int getSelfEmployedSinceInMonths() {
        return selfEmployedSinceInMonths;
    }
    public void setSelfEmployedSinceInMonths(int selfEmployedSinceInMonths) {
        this.selfEmployedSinceInMonths = selfEmployedSinceInMonths;
    }
    public BusinessType getBusinessType() {
        return businessType;
    }
    public void setBusinessType(BusinessType businessType) {
        this.businessType = businessType;
    }
    public String getDateOfIncorporation() {
        return dateOfIncorporation;
    }
    public void setDateOfIncorporation(String dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
    }
    public String getAnnualTurnOverInLacs() {
        return annualTurnOverInLacs;
    }
    public void setAnnualTurnOverInLacs(String annualTurnOverInLacs) {
        this.annualTurnOverInLacs = annualTurnOverInLacs;
    }
    public FirmType getFirm() {
        return firm;
    }
    public void setFirm(FirmType firm) {
        this.firm = firm;
    }
    public Professional getProfession() {
        return profession;
    }
    public void setProfession(Professional profession) {
        this.profession = profession;
    }

    
}
