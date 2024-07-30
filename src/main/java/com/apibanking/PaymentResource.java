package com.apibanking;

import com.apibanking.account.payment.dto.PaymentRequestDTO;
import com.apibanking.account.payment.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/api/payments")
public class PaymentResource {

   @Inject
    PaymentService paymentService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response processPayment(PaymentRequestDTO paymentRequest) throws JsonProcessingException {
            return paymentService.processPayment(paymentRequest);
    }
}
