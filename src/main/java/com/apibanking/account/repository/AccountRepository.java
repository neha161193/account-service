package com.apibanking.account.repository;

import java.util.List;

import com.apibanking.account.entity.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {
    @PersistenceContext
    EntityManager em;

    public List<Account> findByCustomerId(String customerId) {
        String jpql = "SELECT a FROM Account a WHERE a.customerId = :customerId";
        TypedQuery<Account> query = em.createQuery(jpql, Account.class);
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }
    public Account findByCustomerIdAndAccountNo(String customerId, String accountNo) {
        String jpql = "SELECT a FROM Account a WHERE a.customerId = :customerId and a.accountNo = :accountNo";
        TypedQuery<Account> query = em.createQuery(jpql, Account.class);
        query.setParameter("customerId", customerId);
        query.setParameter("accountNo", accountNo);
        return query.getSingleResult();
    }
}
