package com.apibanking.account.payment.repository;

import com.apibanking.account.payment.entity.Transaction;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
 
}
