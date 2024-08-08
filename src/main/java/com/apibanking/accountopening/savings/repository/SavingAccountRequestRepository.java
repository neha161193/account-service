package com.apibanking.accountopening.savings.repository;

import com.apibanking.accountopening.savings.entity.AccountOpeningRequest;

import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SavingAccountRequestRepository implements PanacheRepositoryBase<AccountOpeningRequest, Integer> {

    public Uni<AccountOpeningRequest> findByApplicationNo(String applicationNo) {
        return find("applicationNo", applicationNo).firstResult();
    }

    public Uni<AccountOpeningRequest> findByApplicationNoWithoutUni(String applicationNo) {
        return find("applicationNo", applicationNo).firstResult();
    }
    
    public Uni<AccountOpeningRequest> findByPanNo(String panNo) {
        return find("panNo", panNo).firstResult();
    }

}
