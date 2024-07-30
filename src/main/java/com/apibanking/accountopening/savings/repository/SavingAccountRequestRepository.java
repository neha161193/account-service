package com.apibanking.accountopening.savings.repository;

import com.apibanking.accountopening.savings.entity.AccountOpeningRequest;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SavingAccountRequestRepository implements PanacheRepository<AccountOpeningRequest> {

    public AccountOpeningRequest findByApplicationNo(String applicationNo) {
        return find("applicationNo", applicationNo).firstResult();
    }
    public AccountOpeningRequest findByPanNo(String panNo) {
        return find("panNo", panNo).firstResult();
    }
}
