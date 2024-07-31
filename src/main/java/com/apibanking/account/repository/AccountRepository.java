package com.apibanking.account.repository;

import java.util.List;

import com.apibanking.account.entity.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    @PersistenceContext
    EntityManager em;

    public List<Account> findByCustomerId(String customerId) {
        return list("customerId", customerId);
    }
    public Account findByCustomerIdAndAccountNo(String customerId, String accountNo) {
        return find("customerId = ?1 and accountNo = ?2", customerId, accountNo).singleResult();
    }
}
