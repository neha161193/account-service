package com.apibanking.accountopening.savings.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Introducer {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String customerId;
    @NotNull
    private LocalDate accountOpeningDate;
}
