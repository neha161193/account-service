package com.apibanking.accountopening.current.dto;

import java.time.LocalDate;

import com.apibanking.accountopening.savings.dto.Address;
import com.apibanking.accountopening.savings.dto.Applicant;
import com.apibanking.accountopening.savings.dto.GenderType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AuthorizedSignatoryDetail {
    private Applicant name;
    private Address address;
    @NotNull
    @NotBlank
    private String nationality;

    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]")
    @NotNull
    public String panNo;
        @NotNull
    private GenderType gender;
    private String customerId;
        @NotNull
    private LocalDate dateOfBirth;
    private String mobileNo;
    private String emailId;
    private boolean instaAlert;

}
