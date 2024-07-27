package com.apibanking.accountopening.savings.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PaymentDetail {
    @NotNull
    private PaymentMode paymentMode;
    @NotNull
    private BigDecimal amount;
    @Pattern(regexp = "^\\d{6,10}$")
    private String chequeNo;
    private LocalDate chequeDate;
    @Pattern(regexp = "^[a-zA-Z0-9\\s.&'-]*$")
    private String bankName;
    @Pattern(regexp = "^[a-zA-Z0-9\\s.,'&()-]*$")
    private String bankBranch;
}
