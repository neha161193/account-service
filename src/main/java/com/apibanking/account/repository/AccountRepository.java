package com.apibanking.account.repository;

import com.apibanking.account.entity.Account;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account>{


    public Account findByApplicationNo(String applicationNo) {
        return find("applicationNo", applicationNo).firstResult();
    }

    public Account findByCustomerId(String customerId) {
        return find("customerId", customerId).firstResult();
    }

    public Account findByPanNo(String panNo) {
        return find("panNo", panNo).firstResult();
    }

    public Account findByAadhaarNo(String aadhaarNo) {
        return find("aadhaarNo", aadhaarNo).firstResult();
    }

    public Account findByPanNoAndAadhaarNo(String panNo, String aadhaarNo) {
        return find("panNo", panNo,"aadhaarNo", aadhaarNo).firstResult();
    }
}
