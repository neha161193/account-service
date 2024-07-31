package com.apibanking;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;

import com.apibanking.account.dto.AccountDTO;
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

@Path("/api/v1/accountservice")
public class AccountOpeningResource {

    @Inject
    SavingAccountRequestRepository repository;
    @Inject
    AccountOpeningService service;
    @Inject
    AccountService accountService;

    @POST
    @Path("/saving")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "OpenSavingAccount", description = "This API will help new customers to open their saving account by providing required info. And in reponse applicationNo will be received which will be further used to check the application status")
    public AccountOpeningResponseDTO openSavingAccountRequest(@Valid AccountOpeningRequestDTO accountDto)
            throws JsonProcessingException {
        return service.openSavingAccount(accountDto);
    }

    @POST
    @Path("/current")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "OpenCurrentAccount",
        description = "This API will help new customers to open their current account by providing required info. And in reponse applicationNo will be received which will be further used to check the application status"
    )
    public AccountOpeningResponseDTO openCurrentAccountRequest(@Valid CurrentAccountRequestDTO accountDto)
            throws JsonProcessingException {
        return service.openCurrentAccount(accountDto);
    }

    @POST
    @Path("/fixeddeposit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "OpenFixedDepositAccount",
        description = "This API will help existing customers to open their fixed deposit account by providing required info.And in reponse applicationNo will be received which will be further used to check the application status"
    )
    public AccountOpeningResponseDTO openFixedDepositAccountRequest(@Valid FixedDepositAccountRequestDTO accountDto)
            throws JsonProcessingException {
        return service.openFixedDepositAccount(accountDto);
    }

    @GET
    @Path("/{applicationNo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "GetAccountOpeningStatus",
        description = "This API will help new /existing customers to check their account opening status by providing application No."
    )
    public AccountOpeningStatusDTO getAccountRequest(@PathParam("applicationNo") String applicationNo) {
        return service.getAccount(applicationNo);
    }

    @PATCH

    @Path("/webhook-update")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "UpdateAccountStatusWebhook",
        description = "This webhook API will be called by backend team to update the account opening status"
    )
    public Response updateAccountRequest(@Valid UpdateAccountOpeningStatusDTO accountStatusDto) {
         service.updateAccount(accountStatusDto);
         return Response.status(Response.Status.ACCEPTED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "GetAccount",
        description = "This API will be called by existing customers to check their account info"
    )
    public List<AccountDTO> getAccount(@NotNull @NotBlank @QueryParam("customerId") String customerId, @QueryParam("accountNo") String accountNo) {
        return accountService.getAllAccounts(customerId, accountNo);
    }

    @PATCH
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(
        summary = "UpdateAccount",
        description = "This API will be called by existing customers to update their account info like nominee, address and contact"
    )
    public AccountDTO updateAccountByCustomerIdAndAccountNo(@Valid AccountDTO accountDTO) {
        return accountService.updateAccount(accountDTO);
    }
}
