package com.apibanking.account.repository;

import com.apibanking.account.entity.AccountAddress;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AccountAddressRepository implements PanacheRepositoryBase<AccountAddress, Long> {
    
 
}
