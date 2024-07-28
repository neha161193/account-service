package com.apibanking.account.repository;

import com.apibanking.account.entity.AccountAddress;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AccountAddressRepository implements PanacheRepository<AccountAddress> {
    @Transactional
    public void deleteByAccountId(Long accountId) {
        deleteByAccountId(accountId);
    }
 
}
