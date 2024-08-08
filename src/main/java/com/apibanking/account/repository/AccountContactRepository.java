package com.apibanking.account.repository;

import com.apibanking.account.entity.AccountContact;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountContactRepository implements PanacheRepositoryBase<AccountContact, Long> {

}
