package com.apibanking.account.dto;

import com.apibanking.accountopening.savings.dto.CardType;

import jakarta.validation.constraints.NotNull;

public class AccountDebitCardDetailDTO {
    
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
