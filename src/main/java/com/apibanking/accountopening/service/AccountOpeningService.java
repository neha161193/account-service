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
import com.apibanking.account.entity.AccountAuthorizedSignatory;
import com.apibanking.account.entity.AccountContact;
import com.apibanking.account.entity.AccountDebitCardDetail;
import com.apibanking.account.entity.AccountNominee;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.accountopening.current.dto.CurrentAccountRequestDTO;
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
import com.apibanking.accountopening.savings.entity.AuthorizedSignatoryDetail;
import com.apibanking.accountopening.savings.entity.AccountOpeningRequest;
import com.apibanking.accountopening.savings.repository.SavingAccountRequestRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

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
        AccountOpeningRequest account =  repository.findByApplicationNo(applicationNo);
        AccountOpeningStatusDTO status = modelMapper.map(account, AccountOpeningStatusDTO.class);
        status.setType(account.getAccountType());
        return status;
    }

    @Transactional
    public void updateAccount(UpdateAccountStatusDTO updateAccountStatusDto){
        AccountOpeningRequest savingAccount =  repository.findByApplicationNo(updateAccountStatusDto.getApplicationNo());
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

            List<AccountAuthorizedSignatory> authorizedSignatoryList = modelMapper.map(savingAccount.getAccountAuthorizedSignatory(),
            new TypeToken<List<AccountAuthorizedSignatory>>() {
            }.getType());
            for (AccountAuthorizedSignatory accountAuthorizedSignatory :authorizedSignatoryList){
                accountAuthorizedSignatory.setId(null);
                accountAuthorizedSignatory.setAccount(account);
                AccountAddress address = accountAuthorizedSignatory.getAddress();
                accountAuthorizedSignatory.setAddress(address);
                address.setId(null);
                address.setAccountAuthorizedSignatory(accountAuthorizedSignatory);
            }
            account.setAccountAuthorizedSignatory(authorizedSignatoryList);
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
    public SavingAccountResponseDTO openSavingAccount(SavingAccountRequestDTO accountDto) throws JsonProcessingException{
        // List<Account> account = accountRepository.findByPanNoAndAadhaarNo(accountDto.getPanNo(), accountDto.getAadhaarNo());
        // //TODO: handle exception
        AccountOpeningRequest accountRequest = modelMapper.map(accountDto, AccountOpeningRequest.class);

        List<Address> addressList = new ArrayList<>();
        for (com.apibanking.accountopening.savings.dto.Address address : accountDto.getAddress()) {
            Address addressEntity = modelMapper.map(address, Address.class);
            addressEntity.setAccountOpeningRequest(accountRequest);
            addressList.add(addressEntity);
        }

        Contact contact = accountDto.getContact();
        com.apibanking.accountopening.savings.entity.Contact contactEntity = modelMapper.map(contact,
                com.apibanking.accountopening.savings.entity.Contact.class);
                contactEntity.setAccountOpeningRequest(accountRequest);

        DebitCardDetail debitCardDto = accountDto.getDebitCardDetail();
        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = modelMapper.map(debitCardDto,
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        debitCardDetailEntity.setAccountOpeningRequest(accountRequest);

        Nominee nomineeDto = accountDto.getNominee();
        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = modelMapper.map(nomineeDto,
                com.apibanking.accountopening.savings.entity.Nominee.class);
        nomineeEntity.setAccountOpeningRequest(accountRequest);
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

        SavingAccountResponseDTO response = buildResponse(accountDto.getCustomerId(), accountDto.getType());
        accountRequest.setResponsePayload(ow.writeValueAsString(response));
        accountRequest.setResponseTimestamp(LocalDateTime.now());
        accountRequest.setApplicationNo(response.getApplicationNo());
        accountRequest.setCustomerId(response.getCustomerId());
        accountRequest.setStatus(response.getStatus());
        accountRequest.setId(null);
        repository.persist(accountRequest);
        return response;
    }

    
    private SavingAccountResponseDTO buildResponse(String customerId, AccountType type){
        SavingAccountResponseDTO response = new SavingAccountResponseDTO();
        response.setCustomerId(customerId);
        response.setApplicationNo(String.valueOf(new Random().nextInt(100000)));
        response.setStatus(AccountStatus.UnderInvestigation);
        response.setProductCode("69869");
        response.setType(type);
        return response;

    }

    @Transactional
    public SavingAccountResponseDTO openCurrentAccount(@Valid CurrentAccountRequestDTO accountDto) throws JsonProcessingException {
        AccountOpeningRequest accountRequest = modelMapper.map(accountDto, AccountOpeningRequest.class);

        List<Address> addressList = new ArrayList<>();
        for (com.apibanking.accountopening.savings.dto.Address address : accountDto.getAddress()) {
            Address addressEntity = modelMapper.map(address, Address.class);
            addressEntity.setAccountOpeningRequest(accountRequest);
            addressList.add(addressEntity);
        }

        List<AuthorizedSignatoryDetail> authorizedSignatoryDetailList = new ArrayList<>();
        for (com.apibanking.accountopening.current.dto.AuthorizedSignatoryDetail authorizedSignatoryDetail : accountDto.getAuthorizedSignatoryDetail()) {
            AuthorizedSignatoryDetail authorizedSignatoryDetailEntity = modelMapper.map(authorizedSignatoryDetail, AuthorizedSignatoryDetail.class);
            authorizedSignatoryDetailEntity.setApplicantFirstName(authorizedSignatoryDetail.getApplicant().getFirstName());
            authorizedSignatoryDetailEntity.setApplicantLastName(authorizedSignatoryDetail.getApplicant().getLastName());
            authorizedSignatoryDetailEntity.setApplicantMiddleName(authorizedSignatoryDetail.getApplicant().getMiddleName());
            authorizedSignatoryDetailEntity.setAccountOpeningRequest(accountRequest);
            authorizedSignatoryDetailEntity.getAddress().setAuthorizedSignatoryDetail(authorizedSignatoryDetailEntity);
            authorizedSignatoryDetailList.add(authorizedSignatoryDetailEntity);
        }

        com.apibanking.accountopening.current.dto.Contact contact = accountDto.getContact();
        com.apibanking.accountopening.savings.entity.Contact contactEntity = modelMapper.map(contact,
                com.apibanking.accountopening.savings.entity.Contact.class);
        contactEntity.setOfficeTelephone(contact.getTelephoneNumber1());
        contactEntity.setResidenceTelephone(contact.getTelephoneNumber2());
        contactEntity.setAccountOpeningRequest(accountRequest);

        DebitCardDetail debitCardDto = accountDto.getDebitCardDetail();
        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = modelMapper.map(debitCardDto,
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        debitCardDetailEntity.setAccountOpeningRequest(accountRequest);

        Nominee nomineeDto = accountDto.getNominee();
        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = modelMapper.map(nomineeDto,
                com.apibanking.accountopening.savings.entity.Nominee.class);
        nomineeEntity.setAccountOpeningRequest(accountRequest);
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
        accountRequest.setAccountAuthorizedSignatory(authorizedSignatoryDetailList);

        SavingAccountResponseDTO response = buildResponse(accountDto.getCustomerId(), accountDto.getType());
        accountRequest.setResponsePayload(ow.writeValueAsString(response));
        accountRequest.setResponseTimestamp(LocalDateTime.now());
        accountRequest.setApplicationNo(response.getApplicationNo());
        accountRequest.setCustomerId(response.getCustomerId());
        accountRequest.setStatus(response.getStatus());
        accountRequest.setId(null);
        repository.persist(accountRequest);
        return response;
    }
}
