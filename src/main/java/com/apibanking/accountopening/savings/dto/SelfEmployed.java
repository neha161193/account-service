package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
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
}
