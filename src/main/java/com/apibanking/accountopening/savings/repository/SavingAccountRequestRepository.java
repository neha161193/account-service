package com.apibanking.accountopening.savings.repository;

import com.apibanking.accountopening.savings.entity.SavingAccountRequest;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SavingAccountRequestRepository implements PanacheRepository<SavingAccountRequest> {
    public SavingAccountRequest findByApplicationNo(String applicationNo) {
        return find("applicationNo", applicationNo).firstResult();
    }
}
