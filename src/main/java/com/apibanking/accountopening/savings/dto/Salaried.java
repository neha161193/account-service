package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class Salaried {
    @NotNull
    private SalariedEmploymentType employedWith;

    public SalariedEmploymentType getEmployedWith() {
        return employedWith;
    }

    public void setEmployedWith(SalariedEmploymentType employedWith) {
        this.employedWith = employedWith;
    }

}
