package com.apibanking.accountopening.savings.dto;

import java.math.BigDecimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
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

}
