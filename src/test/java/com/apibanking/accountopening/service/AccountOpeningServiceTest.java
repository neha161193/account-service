package com.apibanking.accountopening.service;

import com.apibanking.account.entity.Account;
import com.apibanking.account.entity.AccountAddress;
import com.apibanking.account.entity.AccountContact;
import com.apibanking.account.entity.AccountDebitCardDetail;
import com.apibanking.account.entity.AccountNominee;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.account.service.AccountValidator;
import com.apibanking.accountopening.current.dto.CurrentAccountRequestDTO;
import com.apibanking.accountopening.fixeddeposit.dto.FixedDepositAccountRequestDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningRequestDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningResponseDTO;
import com.apibanking.accountopening.savings.dto.UpdateAccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.entity.AccountOpeningRequest;
import com.apibanking.accountopening.savings.repository.SavingAccountRequestRepository;
import com.apibanking.accountopening.savings.entity.Address;
import com.apibanking.accountopening.savings.entity.AuthorizedSignatoryDetail;
import com.apibanking.accountopening.savings.entity.Contact;
import com.apibanking.accountopening.savings.entity.DebitCardDetail;
import com.apibanking.accountopening.savings.entity.Nominee;
import com.apibanking.exception.BusinessErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.reflect.Type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountOpeningServiceTest {

    @Mock
    SavingAccountRequestRepository repository;

    @Mock
    AccountRepository accountRepository;

    @Mock
    AccountValidator validator;
    @Mock
    ModelMapper modelMapper;
    private ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    AccountOpeningService accountOpeningService;

    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

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

    @Test
    public void testGetAccount() {
        String applicationNo = "123456";
        AccountOpeningRequest accountRequest = new AccountOpeningRequest();
        accountRequest.setType(AccountType.Saving);
        accountRequest.setCustomerId("123123132");
        accountRequest.setAccountNo("19619836912389");
        accountRequest.setStatus(AccountStatus.Active);

        when(repository.findByApplicationNo(anyString())).thenReturn(accountRequest);
        ModelMapper mm = modelMapper();
        AccountOpeningStatusDTO accountOpeningStatusDTO = mm.map(accountRequest, AccountOpeningStatusDTO.class);
        when(modelMapper.map(accountRequest, AccountOpeningStatusDTO.class)).thenReturn(accountOpeningStatusDTO);

        AccountOpeningStatusDTO result = accountOpeningService.getAccount(applicationNo);
        assertNotNull(result);
        verify(repository, times(1)).findByApplicationNo(applicationNo);
        verify(modelMapper, times(1)).map(accountRequest, AccountOpeningStatusDTO.class);
        assertEquals(AccountStatus.Active, result.getStatus());
    }

    @Test
    public void testUpdateAccount() throws JsonMappingException, JsonProcessingException {
        // Arrange
        UpdateAccountOpeningStatusDTO updateDTO = new UpdateAccountOpeningStatusDTO();
        updateDTO.setApplicationNo("123456");
        updateDTO.setAccountNo("123456789");
        updateDTO.setCustomerId("C123456");
        updateDTO.setStatus(AccountStatus.Active);
        updateDTO.setInterestRate("5.0");
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${panNo}", AccontHelper.getPanNo());
        String sampleRequestBody = readAndReplacePlaceholders("saving-request.json", replacements);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        AccountOpeningRequest accountRequest = objectMapper.readValue(
                sampleRequestBody, AccountOpeningRequest.class);
        when(repository.findByApplicationNo(anyString())).thenReturn(accountRequest);
        AccountContact accountContact = new AccountContact();
        when(modelMapper.map(accountRequest.getContact(), AccountContact.class)).thenReturn(accountContact);
        Account account = new Account();
        when(modelMapper.map(accountRequest, Account.class)).thenReturn(account);
        AccountNominee accountNominee = new AccountNominee();
        AccountAddress nomineeAddress = new AccountAddress();
        accountNominee.setAddress(nomineeAddress);
        when(modelMapper.map(accountRequest.getNominee(), AccountNominee.class)).thenReturn(accountNominee);

        List<AccountAddress> accountAddresses = new ArrayList<>();

        when(modelMapper.map(accountRequest.getAddress(), new TypeToken<List<AccountAddress>>() {
        }.getType())).thenReturn(accountAddresses);

        AccountDebitCardDetail accountDebitCardDetail = new AccountDebitCardDetail();

        when(modelMapper.map(accountRequest.getDebitCardDetail(), AccountDebitCardDetail.class))
                .thenReturn(accountDebitCardDetail);

        doNothing().when(repository).persist(any(AccountOpeningRequest.class));

        accountOpeningService.updateAccount(updateDTO);

        verify(repository, times(1)).findByApplicationNo(updateDTO.getApplicationNo());
        verify(modelMapper, times(1)).map(accountRequest.getContact(), AccountContact.class);
        verify(modelMapper, times(1)).map(accountRequest, Account.class);
        verify(modelMapper, times(1)).map(accountRequest.getNominee(), AccountNominee.class);
        verify(modelMapper, times(1)).map(accountRequest.getAddress(), new TypeToken<List<AccountAddress>>() {
        }.getType());
        verify(modelMapper, times(1)).map(accountRequest.getDebitCardDetail(), AccountDebitCardDetail.class);
        verify(repository, times(1)).persist(any(AccountOpeningRequest.class));
    }

    @Test
    public void testOpenSavingAccount() throws Exception {
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${panNo}", AccontHelper.getPanNo());
        String sampleRequestBody = readAndReplacePlaceholders("saving-request.json", replacements);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        AccountOpeningRequestDTO accountRequest = objectMapper.readValue(
                sampleRequestBody, AccountOpeningRequestDTO.class);
        when(repository.findByPanNo(anyString())).thenReturn(null);

        AccountOpeningRequest accountOpeningRequest = objectMapper.readValue(
                sampleRequestBody, AccountOpeningRequest.class);
        when(modelMapper.map(accountRequest, AccountOpeningRequest.class)).thenReturn(accountOpeningRequest);

        Address addressEntity = new Address();
        when(modelMapper.map(any(com.apibanking.accountopening.savings.dto.Address.class), eq(Address.class)))
                .thenReturn(addressEntity);

        com.apibanking.accountopening.savings.entity.Contact contactEntity = new com.apibanking.accountopening.savings.entity.Contact();
        when(modelMapper.map(accountRequest.getContact(), com.apibanking.accountopening.savings.entity.Contact.class))
                .thenReturn(contactEntity);

        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = new com.apibanking.accountopening.savings.entity.DebitCardDetail();
        when(modelMapper.map(accountRequest.getDebitCardDetail(),
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class)).thenReturn(debitCardDetailEntity);

        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = new com.apibanking.accountopening.savings.entity.Nominee();
        nomineeEntity.setAddress(addressEntity);
        when(modelMapper.map(accountRequest.getNominee(), com.apibanking.accountopening.savings.entity.Nominee.class))
                .thenReturn(nomineeEntity);
        AccountOpeningResponseDTO response = accountOpeningService.openSavingAccount(accountRequest);

        assertThat(response).isNotNull();
        assertThat(response.getType()).isEqualTo(AccountType.Saving);
        verify(repository, times(1)).findByPanNo(accountRequest.getPanNo());
        verify(modelMapper, times(1)).map(accountRequest, AccountOpeningRequest.class);
        verify(modelMapper, times(2)).map(any(com.apibanking.accountopening.savings.dto.Address.class),
                eq(Address.class));
        verify(modelMapper, times(1)).map(accountRequest.getContact(),
                com.apibanking.accountopening.savings.entity.Contact.class);
        verify(modelMapper, times(1)).map(accountRequest.getDebitCardDetail(),
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        verify(modelMapper, times(1)).map(accountRequest.getNominee(),
                com.apibanking.accountopening.savings.entity.Nominee.class);
        verify(repository, times(1)).persist(any(AccountOpeningRequest.class));
    }

    @Test
    public void testOpenCurrentAccount() throws Exception {
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${panNo}", AccontHelper.getPanNo());
        String sampleRequestBody = readAndReplacePlaceholders("current-request.json", replacements);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        CurrentAccountRequestDTO requestDTO = objectMapper.readValue(
                sampleRequestBody, CurrentAccountRequestDTO.class);

        when(repository.findByPanNo(anyString())).thenReturn(null);
        AccountOpeningRequest accountOpeningRequest = objectMapper.readValue(
                sampleRequestBody, AccountOpeningRequest.class);

        when(modelMapper.map(any(CurrentAccountRequestDTO.class),
                any())).thenReturn(accountOpeningRequest);
        Address addressEntity = new Address();
        when(modelMapper.map(any(com.apibanking.accountopening.savings.dto.Address.class), eq(Address.class)))
                .thenReturn(addressEntity);

        com.apibanking.accountopening.savings.entity.Contact contactEntity = new com.apibanking.accountopening.savings.entity.Contact();
        when(modelMapper.map(requestDTO.getContact(), com.apibanking.accountopening.savings.entity.Contact.class))
                .thenReturn(contactEntity);

        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = new com.apibanking.accountopening.savings.entity.DebitCardDetail();
        when(modelMapper.map(requestDTO.getDebitCardDetail(),
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class)).thenReturn(debitCardDetailEntity);

        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = new com.apibanking.accountopening.savings.entity.Nominee();
        nomineeEntity.setAddress(addressEntity);
        when(modelMapper.map(requestDTO.getNominee(), com.apibanking.accountopening.savings.entity.Nominee.class))
                .thenReturn(nomineeEntity);

        AuthorizedSignatoryDetail authorizedSignatoryDetailEntity = new AuthorizedSignatoryDetail();
        when(modelMapper.map(any(com.apibanking.accountopening.current.dto.AuthorizedSignatoryDetail.class),
                eq(AuthorizedSignatoryDetail.class)))
                .thenReturn(authorizedSignatoryDetailEntity);
        authorizedSignatoryDetailEntity.setAddress(addressEntity);
        AccountOpeningResponseDTO response = accountOpeningService.openCurrentAccount(requestDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getType()).isEqualTo(AccountType.Current);
        verify(repository, times(1)).findByPanNo(requestDTO.getPanNo());
        verify(modelMapper, times(1)).map(requestDTO, AccountOpeningRequest.class);
        verify(modelMapper, times(1)).map(
                any(com.apibanking.accountopening.current.dto.AuthorizedSignatoryDetail.class),
                eq(AuthorizedSignatoryDetail.class));

        verify(modelMapper, times(2)).map(any(com.apibanking.accountopening.savings.dto.Address.class),
                eq(Address.class));
        verify(modelMapper, times(1)).map(requestDTO.getContact(),
                com.apibanking.accountopening.savings.entity.Contact.class);
        verify(modelMapper, times(1)).map(requestDTO.getDebitCardDetail(),
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        verify(modelMapper, times(1)).map(requestDTO.getNominee(),
                com.apibanking.accountopening.savings.entity.Nominee.class);
        verify(repository, times(1)).persist(any(AccountOpeningRequest.class));
    }

    @Test
    public void testOpenFixedDepositAccount() throws Exception {
        String customerId = AccontHelper.getCustomerId();
        Map<String, String> replacements = new HashMap<String, String>();
        replacements.put("${debitAccountNo}", AccontHelper.getAccountNo());
        replacements.put("${customerId}", customerId);
        String sampleRequestBody = readAndReplacePlaceholders("fixeddeposit-request.json", replacements);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());

        FixedDepositAccountRequestDTO requestDTO = objectMapper.readValue(
                sampleRequestBody, FixedDepositAccountRequestDTO.class);

        Account account = mock(Account.class);
        when(validator.validateCustomerIdAndAccountNo(anyString(),
                anyString())).thenReturn(account);
        AccountOpeningRequest accountOpeningRequest = objectMapper.readValue(
                sampleRequestBody, AccountOpeningRequest.class);

        when(modelMapper.map(any(FixedDepositAccountRequestDTO.class),
                any())).thenReturn(accountOpeningRequest);

        Address addressEntity = new Address();

        List<Address> addresses = new ArrayList<>();
        Type listType = new TypeToken<List<Address>>() {
        }.getType();

        when(modelMapper.map(anyList(), eq(listType))).thenReturn(addresses);

        com.apibanking.accountopening.savings.entity.Contact contactEntity = new com.apibanking.accountopening.savings.entity.Contact();
        when(modelMapper.map(account.getContact(), com.apibanking.accountopening.savings.entity.Contact.class))
                .thenReturn(contactEntity);

        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = new com.apibanking.accountopening.savings.entity.DebitCardDetail();
        when(modelMapper.map(account.getDebitCardDetail(),
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class)).thenReturn(debitCardDetailEntity);

        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = new com.apibanking.accountopening.savings.entity.Nominee();
        nomineeEntity.setAddress(addressEntity);
        when(modelMapper.map(account.getNomineeDetail(), com.apibanking.accountopening.savings.entity.Nominee.class))
                .thenReturn(nomineeEntity);
        when(account.getAccountBalance()).thenReturn(new BigDecimal("1000"));

        // Act
        AccountOpeningResponseDTO response = accountOpeningService.openFixedDepositAccount(requestDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getCustomerId()).isEqualTo(customerId);
        assertThat(response.getType()).isEqualTo(AccountType.FixedDeposit);
        verify(validator, times(1)).validateCustomerIdAndAccountNo(anyString(),
                anyString());

        verify(modelMapper, times(1)).map(anyList(), eq(listType));
        verify(modelMapper, times(1)).map(any(FixedDepositAccountRequestDTO.class),
                any());
        verify(modelMapper, times(1)).map(account.getContact(),
                com.apibanking.accountopening.savings.entity.Contact.class);
        verify(modelMapper, times(1)).map(account.getDebitCardDetail(),
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        verify(modelMapper, times(1)).map(account.getNomineeDetail(),
                com.apibanking.accountopening.savings.entity.Nominee.class);
        verify(repository, times(1)).persist(any(AccountOpeningRequest.class));
    }
}
