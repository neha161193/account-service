package com.apibanking.account.payment.repository;

import com.apibanking.account.payment.entity.Transaction;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
 
}
