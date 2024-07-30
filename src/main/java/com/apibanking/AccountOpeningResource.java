package com.apibanking;

import java.util.List;

import com.apibanking.account.dto.AccountDTO;
import com.apibanking.account.entity.Account;
import com.apibanking.accountopening.current.dto.CurrentAccountRequestDTO;
import com.apibanking.accountopening.fixeddeposit.dto.FixedDepositAccountRequestDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningRequestDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningResponseDTO;
import com.apibanking.accountopening.savings.dto.UpdateAccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.repository.SavingAccountRequestRepository;
import com.apibanking.accountopening.service.AccountOpeningService;
import com.apibanking.accountopening.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/accounts")
public class AccountOpeningResource {

    @Inject
    SavingAccountRequestRepository repository;
    @Inject
    AccountOpeningService service;
    @Inject
    AccountService accountService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountOpeningResponseDTO openSavingAccountRequest(@Valid AccountOpeningRequestDTO accountDto)
            throws JsonProcessingException {
        return service.openSavingAccount(accountDto);
    }
    @POST
    @Path("/current")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountOpeningResponseDTO openCurrentAccountRequest(@Valid CurrentAccountRequestDTO accountDto)
            throws JsonProcessingException {
        return service.openCurrentAccount(accountDto);
    }

    @POST
    @Path("/fd")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public AccountOpeningResponseDTO openFixedDepositAccountRequest(@Valid FixedDepositAccountRequestDTO accountDto)
            throws JsonProcessingException {
        return service.openFixedDepositAccount(accountDto);
    }

    @GET
    @Path("/{applicationNo}")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountOpeningStatusDTO getAccountRequest(@PathParam("applicationNo") String applicationNo) {
        return service.getAccount(applicationNo);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccountRequest(@Valid UpdateAccountOpeningStatusDTO accountStatusDto) {
         service.updateAccount(accountStatusDto);
         return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AccountDTO> getAccount(@NotNull @NotBlank @QueryParam("customerId") String customerId, @QueryParam("accountNo") String accountNo) {
        return accountService.getAllAccounts(customerId, accountNo);
    }

    @PATCH
    @Path("update")
    @Produces(MediaType.APPLICATION_JSON)
    public AccountDTO updateAccountByCustomerIdAndAccountNo(@Valid AccountDTO accountDTO) {
        return accountService.updateAccount(accountDTO);
    }
}
