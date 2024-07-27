package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Salaried {
    @NotNull
    private SalariedEmploymentType employedWith;

}
