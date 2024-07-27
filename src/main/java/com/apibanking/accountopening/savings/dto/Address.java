package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Address {
    @NotNull
    private AddressType addressType;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,#-]*$")
    private String line1;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,#-]*$")
    private String line2;
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,#-]*$")
    private String line3;
    @NotNull
    private String city;
    @NotNull
    @Pattern(regexp = "^[1-9][0-9]{5}$")
    private String pinCode;
    @NotNull
    @Pattern(regexp = "^[a-zA-Z\\s-]*$")
    private String state;
    @NotNull
    private String country;

}
