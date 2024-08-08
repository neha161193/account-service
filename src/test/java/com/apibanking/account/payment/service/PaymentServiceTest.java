package com.apibanking.account.payment.service;

import com.apibanking.account.entity.Account;
import com.apibanking.account.payment.dto.PaymentRequestDTO;
import com.apibanking.account.payment.dto.PaymentResponseDTO;
import com.apibanking.account.payment.dto.Status;
import com.apibanking.account.payment.entity.Transaction;
import com.apibanking.account.payment.repository.TransactionRepository;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.account.service.AccountValidator;
import com.apibanking.accountopening.service.AccontHelper;
import com.apibanking.exception.BusinessErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import jakarta.persistence.NoResultException;
import jakarta.ws.rs.core.Response;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountValidator validator;
    @Mock
    ModelMapper modelMapper;
    @Mock
    private Logger log;
    private ObjectMapper objectMapper = new ObjectMapper();

    private String readAndReplacePlaceholders(String fileName, Map<String, String> replacements) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // Replace placeholders
            String content = sb.toString();
            for (Map.Entry<String, String> entry : replacements.entrySet()) {
                content = content.replace(entry.getKey(), entry.getValue());
            }
            return content;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to read or process the JSON file", e);
        }
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Optionally, you can configure behavior for @ConfigProperty injections here if
        // needed.
    }

    // @Test
    // public void testProcessPaymentSuccess() throws JsonProcessingException {
    //     // Mock the validator to return a valid account
    //     Account mockAccount = new Account();
    //     mockAccount.setAccountBalance(BigDecimal.valueOf(1000));
    //     when(validator.validateCustomerIdAndAccountNo(anyString(), anyString())).thenReturn(mockAccount);

    //     // Mock ObjectMapper behavior
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    //     // Create a PaymentRequestDTO
    //     Map<String, String> replacements = new HashMap<String, String>();
    //     replacements.put("${accountNo}", AccontHelper.getAccountNo());
    //     replacements.put("${customerId}", AccontHelper.getCustomerId());
    //     replacements.put("${amount}", "1000");
    //     String sampleRequestBody = readAndReplacePlaceholders("payment.json", replacements);
    //     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //     objectMapper.registerModule(new JavaTimeModule());

    //     PaymentRequestDTO requestDTO = objectMapper.readValue(
    //             sampleRequestBody, PaymentRequestDTO.class);
    //             Transaction transaction = new Transaction();
    //                     when(modelMapper.map(requestDTO, Transaction.class)).thenReturn(transaction);
    //     // Call the method to test
    //     Response response = paymentService.processPayment(requestDTO);

    //     // Verify the response
    //     assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    //     PaymentResponseDTO responseDTO = (PaymentResponseDTO) response.getEntity();
    //     assertNotNull(responseDTO);
    //     assertEquals(Status.Success, responseDTO.getStatus());

    //     // Verify interactions
    //     verify(transactionRepository, times(1)).persist(any(Transaction.class));
    //     verify(modelMapper, times(1)).map(requestDTO, Transaction.class);
    //     verify(accountRepository, times(1)).persist(any(Account.class));
    // }

    // @Test
    // public void testProcessPaymentFailure() throws JsonProcessingException {
    //     // Mock the validator to return a valid account
    //     Account mockAccount = new Account();
    //     mockAccount.setAccountBalance(BigDecimal.valueOf(1000));
    //     when(validator.validateCustomerIdAndAccountNo(anyString(), anyString())).thenReturn(mockAccount);

    //     // Mock ObjectMapper behavior
    //     ObjectMapper objectMapper = new ObjectMapper();
    //     objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        
    //     // Create a PaymentRequestDTO with invalid amount
    //     Map<String, String> replacements = new HashMap<String, String>();
    //     replacements.put("${accountNo}", AccontHelper.getAccountNo());
    //     replacements.put("${customerId}", AccontHelper.getCustomerId());
    //     replacements.put("${amount}", "-1000");
    //     String sampleRequestBody = readAndReplacePlaceholders("payment.json", replacements);
    //     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //     objectMapper.registerModule(new JavaTimeModule());

    //     PaymentRequestDTO requestDTO = objectMapper.readValue(
    //             sampleRequestBody, PaymentRequestDTO.class);
    //             Transaction transaction = new Transaction();
    //             when(modelMapper.map(requestDTO, Transaction.class)).thenReturn(transaction);
        
    //     // Call the method to test
    //     Response response = paymentService.processPayment(requestDTO);

    //     // Verify the response
    //     assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    //     PaymentResponseDTO responseDTO = (PaymentResponseDTO) response.getEntity();
    //     assertNotNull(responseDTO);
    //     assertEquals(Status.Failed, responseDTO.getStatus());

    //     // Verify interactions
    //     verify(transactionRepository, times(1)).persist(any(Transaction.class));
    //     verify(modelMapper, times(1)).map(requestDTO, Transaction.class);
    //     verify(accountRepository, never()).persist(any(Account.class));
    // }

    // @Test
    // public void testProcessPaymentNoResultException() throws JsonMappingException, JsonProcessingException {
    //     // Mock the validator to throw NoResultException
    //     when(validator.validateCustomerIdAndAccountNo(anyString(), anyString())).thenThrow(new NoResultException());

    //     // Create a PaymentRequestDTO
    //     String customerId = AccontHelper.getCustomerId();
    //     String accountNo = AccontHelper.getAccountNo();
    //     Map<String, String> replacements = new HashMap<String, String>();
    //     replacements.put("${accountNo}", accountNo);
    //     replacements.put("${customerId}", customerId);
    //     replacements.put("${amount}", "1000");
    //     String sampleRequestBody = readAndReplacePlaceholders("payment.json", replacements);
    //     objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    //     objectMapper.registerModule(new JavaTimeModule());

    //     PaymentRequestDTO requestDTO = objectMapper.readValue(
    //             sampleRequestBody, PaymentRequestDTO.class);
    //     // Call the method to test
    //     BusinessErrorException thrown = assertThrows(BusinessErrorException.class, () -> {
    //         paymentService.processPayment(requestDTO);
    //     });

    //     // Verify the exception message
    //     assertEquals("No Record Found for customerId " + customerId + " and accountNo " + accountNo, thrown.getDetail());
    //     assertEquals("NOT_FOUND", thrown.getMessage());

    // }
}
