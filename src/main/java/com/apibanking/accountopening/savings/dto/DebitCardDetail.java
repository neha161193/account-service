package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DebitCardDetail {
    @NotNull
    private boolean optForCard;
    private CardType cardType;
}
