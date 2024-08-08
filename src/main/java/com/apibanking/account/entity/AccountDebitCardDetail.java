package com.apibanking.account.entity;

import com.apibanking.accountopening.savings.dto.CardType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="account_debitcarddetail")
public class AccountDebitCardDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private boolean optForCard;
    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
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
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    
}
