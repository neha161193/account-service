package com.apibanking.accountopening.savings.entity;

import com.apibanking.accountopening.savings.dto.CardType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class DebitCardDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private boolean optForCard;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountOpeningRequest_id")
    private AccountOpeningRequest accountOpeningRequest;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public AccountOpeningRequest getAccountOpeningRequest() {
        return accountOpeningRequest;
    }
    public void setAccountOpeningRequest(AccountOpeningRequest accountOpeningRequest) {
        this.accountOpeningRequest = accountOpeningRequest;
    }

    
}
