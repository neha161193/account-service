package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Contact {
    @NotNull
    @NotBlank
    private String residenceTelephone;
    @NotNull
    @NotBlank
    private String officeTelephone;
    @NotNull
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    @Pattern(regexp = "^\\d{1,14}$")
    @NotNull
    private String mobileNumber;
    private String mobileNumberServiceProvider;
    private boolean instaAlert;
}
