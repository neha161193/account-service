package com.apibanking.account.repository;

import com.apibanking.account.entity.AccountNominee;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountNomineeRepository implements PanacheRepositoryBase<AccountNominee, Long> {

}
