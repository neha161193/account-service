package com.apibanking.accountopening.savings.dto;

import jakarta.validation.constraints.NotNull;

public class DebitCardDetail {
    @NotNull
    private boolean optForCard;
    private CardType cardType;
    public boolean isOptForCard() {
        return optForCard;
    }
    public void setOptForCard(boolean optForCard) {
        this.optForCard = optForCard;
    }
    public CardType getCardType() {
        return cardType;
    }
    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }
}
