package com.apibanking.account.repository;

import com.apibanking.account.entity.AccountNominee;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountNomineeRepository implements PanacheRepository<AccountNominee> {

}
