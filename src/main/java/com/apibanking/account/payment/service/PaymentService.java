package com.apibanking.account.payment.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import com.apibanking.account.entity.Account;
import com.apibanking.account.payment.dto.PaymentRequestDTO;
import com.apibanking.account.payment.dto.PaymentResponseDTO;
import com.apibanking.account.payment.dto.Status;
import com.apibanking.account.payment.entity.Transaction;
import com.apibanking.account.payment.repository.TransactionRepository;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.account.service.AccountValidator;
import com.apibanking.exception.BusinessErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@ApplicationScoped
public class PaymentService {
    @Inject
    @ConfigProperty(name = "mock.payment.gateway.success.message")
    String successMessage;

    @Inject
    @ConfigProperty(name = "mock.payment.gateway.failure.message")
    String failureMessage;

    @Inject
    TransactionRepository transactionRepository;
@   Inject
    AccountRepository accountRepository;
    @Inject
    AccountValidator validator;
    @Inject
    ModelMapper modelMapper;
    
    private static final Logger LOG = Logger.getLogger(PaymentService.class);

    @Transactional
    public Response processPayment(PaymentRequestDTO paymentRequest) throws JsonProcessingException {
        try {
            Account account = validator.validateCustomerIdAndAccountNo(
                    paymentRequest.getToAccount().getCustomerId(),
                    paymentRequest.getToAccount().getAccountNo());
            ObjectMapper om = new ObjectMapper();
            om = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
            Transaction transaction = modelMapper.map(paymentRequest, Transaction.class);
            transaction.setFromAccountBankIFSC(paymentRequest.getFromAccount().getIfscCode());
            transaction.setFromAccountBankName(paymentRequest.getFromAccount().getBankName());
            transaction.setFromAccountNo(paymentRequest.getFromAccount().getAccountNo());
            transaction.setToAccountCustomerId(paymentRequest.getToAccount().getCustomerId());
            transaction.setToAccountNo(paymentRequest.getToAccount().getAccountNo());
            transaction.setRequestPayload(ow.writeValueAsString(paymentRequest));
            transaction.setRequestTimestamp(LocalDateTime.now());

            if (paymentRequest.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                LOG.infof("Processing payment: Account=%s, Amount=%.2f", paymentRequest.getToAccount().getAccountNo(),
                        paymentRequest.getAmount());
                transaction.setResponseTimestamp(LocalDateTime.now());
                transaction.setStatus(Status.Success);

                PaymentResponseDTO response = buildResponse(successMessage, Status.Success);

                transaction.setResponsePayload(ow.writeValueAsString(response));
                transaction.setTransactionReferenceNo(response.getTransactionReferenceNo());
                account.setAccountBalance(account.getAccountBalance().add(paymentRequest.getAmount()));
                accountRepository.persist(account);
                transactionRepository.persist(transaction);
                return Response.ok(response).build();
            } else {
                LOG.warnf("Payment failed: Account=%s, Amount=%.2f", paymentRequest.getToAccount().getAccountNo(),
                        paymentRequest.getAmount());
                transaction.setResponseTimestamp(LocalDateTime.now());
                transaction.setStatus(Status.Failed);

                PaymentResponseDTO response = buildResponse(failureMessage, Status.Failed);
                transaction.setResponsePayload(ow.writeValueAsString(response));
                transaction.setTransactionReferenceNo(response.getTransactionReferenceNo());
                transactionRepository.persist(transaction);
                return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
            }
        } catch (NoResultException ex) {
            throw new BusinessErrorException(
                    "No Record Found for customerId " + paymentRequest.getToAccount().getCustomerId()
                            + " and accountNo " + paymentRequest.getToAccount().getAccountNo(),
                    Response.Status.NOT_FOUND);
        }
    }

    private PaymentResponseDTO buildResponse(String remarks, Status status) {
        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setRemarks(remarks);
        response.setStatus(status);
        response.setTransactionReferenceNo(String.valueOf(new Random().nextInt(100000)));
        return response;
    }
}
