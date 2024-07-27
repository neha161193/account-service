package com.apibanking.accountopening.savings.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Nominee {
    @NotNull
    private boolean optForNominee;
    private String name;
    private Address address;
    private String residenceTelephone;
    private String relationWithApplicant;
    private LocalDate dateOfBirth;
    private String mobileNumber;
}
