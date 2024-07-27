package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Applicant {
    @NotNull
    @Pattern(regexp = "^(Mr\\.|Mrs\\.|Ms\\.|Dr\\.)$")
    private String prefix;

    @Pattern(regexp = "^[a-zA-Z\\s'-]+$")
    @NotNull
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z\\s'-]+$")
    private String middleName;

    @Pattern(regexp = "^[a-zA-Z\\s'-]+$")
    @NotNull
    private String lastName;
}
