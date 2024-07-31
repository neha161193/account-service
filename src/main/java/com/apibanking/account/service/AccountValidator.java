package com.apibanking.account.service;

import com.apibanking.account.entity.Account;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.exception.BusinessErrorException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class AccountValidator {
    @Inject
    AccountRepository accountRepository;

    public Account validateCustomerIdAndAccountNo(String customerId, String accountNo) {
        try {
            return accountRepository.findByCustomerIdAndAccountNo(customerId, accountNo);
        } catch (NoResultException ex) {
            throw new BusinessErrorException(
                    "No Record Found for customerId " + customerId + " and accountNo " + accountNo,
                    Status.NOT_FOUND);
        }
    }
}
