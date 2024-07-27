package com.apibanking.accountopening.savings.dto;

import lombok.Data;

@Data
public class SelfEmployed {
    private int selfEmployedSinceInYears;
    private int selfEmployedSinceInMonths;
    private BusinessType businessType;
    private String dateOfIncorporation;
    private String annualTurnOverInLacs;
    private FirmType firm;
    private Professional profession;
}
