package com.apibanking.account.service;

import com.apibanking.account.entity.Account;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.exception.BusinessErrorException;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class AccountValidator {
    @Inject
    AccountRepository accountRepository;

    public Uni<Account> validateCustomerIdAndAccountNo(String customerId, String accountNo) {
            return accountRepository.findByCustomerIdAndAccountNo(customerId, accountNo)
            .onFailure().transform(ex -> {
                if (ex instanceof NoResultException) {
                    return new BusinessErrorException(
                        "No Record Found for customerId " + customerId + " and accountNo " + accountNo,
                        Status.NOT_FOUND
                    );
                }
                // Transform other exceptions or rethrow them
                return ex;
            });
    }
    
}
