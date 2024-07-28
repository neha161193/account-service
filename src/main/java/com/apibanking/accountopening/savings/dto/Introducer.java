package com.apibanking.accountopening.savings.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class Introducer {
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String customerId;
    @NotNull
    private LocalDate accountOpeningDate;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public LocalDate getAccountOpeningDate() {
        return accountOpeningDate;
    }
    public void setAccountOpeningDate(LocalDate accountOpeningDate) {
        this.accountOpeningDate = accountOpeningDate;
    }
    
}
