package com.apibanking;

import java.io.IOException;
import java.time.temporal.ChronoUnit;

import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.openapi.annotations.Operation;

import com.apibanking.account.payment.dto.PaymentRequestDTO;
import com.apibanking.account.payment.service.PaymentService;
import com.apibanking.exception.BusinessErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/v1/accountservice")
public class PaymentResource {

   @Inject
    PaymentService paymentService;

    @POST
    @Path("payment")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
        @Operation(
        summary = "ProcessPayment",
        description = "This API will be called by existing customers to transfer funds to their savings or current account."
    )
    @Retry(delayUnit = ChronoUnit.SECONDS, maxRetries = 3, delay = 5, retryOn= BusinessErrorException.class)
    public Response processPayment(PaymentRequestDTO paymentRequest) throws JsonProcessingException {
            return paymentService.processPayment(paymentRequest);
    }
}
