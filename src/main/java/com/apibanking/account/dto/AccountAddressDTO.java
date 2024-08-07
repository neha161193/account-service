package com.apibanking.account.dto;

import java.io.Serializable;

import com.apibanking.accountopening.savings.dto.AddressType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AccountAddressDTO implements Serializable {

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

    public AddressType getAddressType() {
        return addressType;
    }
    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }
    public String getLine1() {
        return line1;
    }
    public void setLine1(String line1) {
        this.line1 = line1;
    }
    public String getLine2() {
        return line2;
    }
    public void setLine2(String line2) {
        this.line2 = line2;
    }
    public String getLine3() {
        return line3;
    }
    public void setLine3(String line3) {
        this.line3 = line3;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPinCode() {
        return pinCode;
    }
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

}
