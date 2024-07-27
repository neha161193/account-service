package com.apibanking;

import com.apibanking.accountopening.savings.dto.AccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.dto.SavingAccountRequestDTO;
import com.apibanking.accountopening.savings.dto.SavingAccountResponseDTO;
import com.apibanking.accountopening.savings.dto.UpdateAccountStatusDTO;
import com.apibanking.accountopening.savings.entity.SavingAccountRequest;
import com.apibanking.accountopening.savings.repository.SavingAccountRequestRepository;
import com.apibanking.accountopening.service.AccountOpeningService;

import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/accounts")
public class AccountOpeningResource {

    @Inject
    SavingAccountRequestRepository repository;
    @Inject
    AccountOpeningService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SavingAccountResponseDTO openAccountRequest(@Valid SavingAccountRequestDTO accountDto)
            throws JsonProcessingException {
        return service.persist(accountDto);
    }

    @GET
    @Path("/{applicationNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountOpeningStatusDTO getAccountRequest(@PathParam("applicationNo") String applicationNo) {
        return service.getAccount(applicationNo);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public void updateAccountRequest(@Valid UpdateAccountStatusDTO accountStatusDto) {
         service.updateAccount(accountStatusDto);
    }
}
