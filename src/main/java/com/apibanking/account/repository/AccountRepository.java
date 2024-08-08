package com.apibanking.account.repository;

import java.util.List;

import com.apibanking.account.entity.Account;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
@ApplicationScoped
public class AccountRepository implements PanacheRepositoryBase<Account, Long> {

    public Uni<List<Account>> findByCustomerId(String customerId) {
        return list("customerId", customerId);
    }
    public Uni<Account> findByCustomerIdAndAccountNo(String customerId, String accountNo) {
        return find("customerId = ?1 and accountNo = ?2", customerId, accountNo).singleResult();
    }
}
