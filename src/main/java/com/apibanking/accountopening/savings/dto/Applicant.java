package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
