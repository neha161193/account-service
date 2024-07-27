package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SavingAccountResponseDTO {
    private String customerId;
    @NotNull
    private String applicationNo;
    @NotNull
    private AccountStatus status;
    @NotNull
    private AccountType type;
    private String productCode;
}
