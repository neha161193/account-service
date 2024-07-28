package com.apibanking.accountopening.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.apibanking.account.entity.Account;
import com.apibanking.account.entity.AccountAddress;
import com.apibanking.account.entity.AccountContact;
import com.apibanking.account.entity.AccountDebitCardDetail;
import com.apibanking.account.entity.AccountNominee;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.accountopening.savings.dto.AccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.dto.Contact;
import com.apibanking.accountopening.savings.dto.DebitCardDetail;
import com.apibanking.accountopening.savings.dto.Nominee;
import com.apibanking.accountopening.savings.dto.SavingAccountRequestDTO;
import com.apibanking.accountopening.savings.dto.SavingAccountResponseDTO;
import com.apibanking.accountopening.savings.dto.UpdateAccountStatusDTO;
import com.apibanking.accountopening.savings.entity.Address;
import com.apibanking.accountopening.savings.entity.SavingAccountRequest;
import com.apibanking.accountopening.savings.repository.SavingAccountRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AccountOpeningService {
    @Inject
    SavingAccountRequestRepository repository;
    @Inject
    AccountRepository accountRepository;
    @Inject
    ModelMapper modelMapper;

    @Transactional
    public AccountOpeningStatusDTO getAccount(String applicationNo){
        SavingAccountRequest account =  repository.findByApplicationNo(applicationNo);
        AccountOpeningStatusDTO status = modelMapper.map(account, AccountOpeningStatusDTO.class);
        status.setType(account.getAccountType());
        return status;
    }

    @Transactional
    public void updateAccount(UpdateAccountStatusDTO updateAccountStatusDto){
        SavingAccountRequest savingAccount =  repository.findByApplicationNo(updateAccountStatusDto.getApplicationNo());
        if (savingAccount != null) {
            savingAccount.setAccountNo(updateAccountStatusDto.getAccountNo());
            savingAccount.setCustomerId(updateAccountStatusDto.getCustomerId());
            savingAccount.setStatus(updateAccountStatusDto.getStatus());
            repository.persist(savingAccount);

            Account account = modelMapper.map(savingAccount, Account.class);
            AccountContact accountContact = modelMapper.map(savingAccount.getContact(), AccountContact.class);
            accountContact.setId(null);
            accountContact.setAccount(account);
            account.setContact(accountContact);

            AccountNominee accountNominee = modelMapper.map(savingAccount.getNominee(), AccountNominee.class);
            accountNominee.setId(null);
            accountNominee.getAddress().setId(null);
            accountNominee.setAccount(account);
            accountNominee.getAddress().setAccountNominee(accountNominee);
            account.setNomineeDetail(accountNominee);

            List<AccountAddress> accountAddresses = modelMapper.map(savingAccount.getAddress(),
                    new TypeToken<List<AccountAddress>>() {
                    }.getType());
                    for (AccountAddress accountAddress :accountAddresses){
                        accountAddress.setId(null);
                        accountAddress.setAccount(account);
                    }
            account.setAddress(accountAddresses);
            account.setId(null);

            AccountDebitCardDetail accountDebitCardDetail = modelMapper.map(savingAccount.getDebitCardDetail(), AccountDebitCardDetail.class);
            accountDebitCardDetail.setId(null);
            accountDebitCardDetail.setAccount(account);
            account.setDebitCardDetail(accountDebitCardDetail);
            account.setAccountOpeningDate(LocalDate.now());
            account.setAccountHolderName(savingAccount.getApplicantFirstName() + " " + savingAccount.getApplicantLastName());
            account.setAccountBalance(savingAccount.getRequiredAverageBalance());
            account.setInterestRate(updateAccountStatusDto.getInterestRate());
            accountRepository.persist(account);
        }
    }

    @Transactional
    public SavingAccountResponseDTO persist(SavingAccountRequestDTO accountDto) throws JsonProcessingException{
        SavingAccountRequest accountRequest = modelMapper.map(accountDto, SavingAccountRequest.class);

        List<Address> addressList = new ArrayList<>();
        for (com.apibanking.accountopening.savings.dto.Address address : accountDto.getAddress()) {
            Address addressEntity = modelMapper.map(address, Address.class);
            addressEntity.setSavingAccountRequest(accountRequest);
            addressList.add(addressEntity);
        }

        Contact contact = accountDto.getContact();
        com.apibanking.accountopening.savings.entity.Contact contactEntity = modelMapper.map(contact,
                com.apibanking.accountopening.savings.entity.Contact.class);
                contactEntity.setSavingAccountRequest(accountRequest);

        DebitCardDetail debitCardDto = accountDto.getDebitCardDetail();
        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = modelMapper.map(debitCardDto,
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        debitCardDetailEntity.setSavingAccountRequest(accountRequest);

        Nominee nomineeDto = accountDto.getNominee();
        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = modelMapper.map(nomineeDto,
                com.apibanking.accountopening.savings.entity.Nominee.class);
        nomineeEntity.setSavingAccountRequest(accountRequest);
        nomineeEntity.getAddress().setNominee(nomineeEntity);
        addressList.add(nomineeEntity.getAddress());
        
        ObjectMapper om = new ObjectMapper();
        om = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
        accountRequest.setAccountType(accountDto.getType());
        accountRequest.setApplicantFirstName(accountDto.getApplicant().getFirstName());
        accountRequest.setApplicantLastName(accountDto.getApplicant().getLastName());
        accountRequest.setRequestPayload(ow.writeValueAsString(accountDto));
        accountRequest.setContact(contactEntity);
        accountRequest.setDebitCardDetail(debitCardDetailEntity);
        accountRequest.setNominee(nomineeEntity);
        accountRequest.setAddress(addressList);

        SavingAccountResponseDTO response = buildResponse(accountDto.getCustomerId());
        accountRequest.setResponsePayload(ow.writeValueAsString(response));
        accountRequest.setResponseTimestamp(LocalDateTime.now());
        accountRequest.setApplicationNo(response.getApplicationNo());
        accountRequest.setCustomerId(response.getCustomerId());
        accountRequest.setStatus(response.getStatus());
        accountRequest.setId(null);
        repository.persist(accountRequest);
        return response;
    }

    
    private SavingAccountResponseDTO buildResponse(String customerId){
        SavingAccountResponseDTO response = new SavingAccountResponseDTO();
        response.setCustomerId(customerId);
        response.setApplicationNo(String.valueOf(new Random().nextInt(100000)));
        response.setStatus(AccountStatus.UnderInvestigation);
        response.setProductCode("69869");
        response.setType(AccountType.Savings);
        return response;

    }
}
