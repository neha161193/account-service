package com.apibanking.account.repository;

import com.apibanking.account.entity.AccountContact;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountContactRepository implements PanacheRepository<AccountContact> {

}
