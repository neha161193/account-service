package com.apibanking.accountopening.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.modelmapper.ModelMapper;

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

    @Transactional
    public AccountOpeningStatusDTO getAccount(String applicationNo){
        SavingAccountRequest account =  repository.findByApplicationNo(applicationNo);
        AccountOpeningStatusDTO status = new AccountOpeningStatusDTO();
        status.setCustomerId(account.getCustomerId());
        status.setAccountNumber(account.getAccountNumber());
        status.setStatus(account.getStatus());
        status.setType(account.getAccountType());
        return status;
    }
    @Transactional
    public void updateAccount(UpdateAccountStatusDTO updateAccountStatusDto){
        SavingAccountRequest account =  repository.findByApplicationNo(updateAccountStatusDto.getApplicationNo());
        if (account != null){
            account.setAccountNumber(updateAccountStatusDto.getAccountNumber());
            account.setCustomerId(updateAccountStatusDto.getCustomerId());
            account.setStatus(updateAccountStatusDto.getStatus());
            repository.persist(account);
        }
    }

    @Transactional
    public SavingAccountResponseDTO persist(SavingAccountRequestDTO accountDto) throws JsonProcessingException{
        ModelMapper modelMapper = new ModelMapper();
        SavingAccountRequest accountRequest = modelMapper.map(accountDto, SavingAccountRequest.class);

        List<Address> addressList = new ArrayList<>();
        for (com.apibanking.accountopening.savings.dto.Address address : accountDto.getAddress()) {
            Address addressEntity = modelMapper.map(address, Address.class);
            addressList.add(setAddress( addressEntity, address));
        }

        Contact contact = accountDto.getContact();
        com.apibanking.accountopening.savings.entity.Contact contactEntity = modelMapper.map(contact,
                com.apibanking.accountopening.savings.entity.Contact.class);
        contactEntity = setContact(contactEntity,  contact);

        DebitCardDetail debitCardDto = accountDto.getDebitCardDetail();
        com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = modelMapper.map(debitCardDto,
                com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
        debitCardDetailEntity = setDebitCardDetail(debitCardDetailEntity, debitCardDto);

        Nominee nomineeDto = accountDto.getNominee();
        com.apibanking.accountopening.savings.entity.Nominee nomineeEntity = modelMapper.map(nomineeDto,
                com.apibanking.accountopening.savings.entity.Nominee.class);
        nomineeEntity = setNominee( modelMapper, nomineeEntity, nomineeDto);

        ObjectMapper om = new ObjectMapper();
        om = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
        accountRequest.setRequestPayload(ow.writeValueAsString(accountDto));
        accountRequest.setAddress(addressList);
        accountRequest.setContact(contactEntity);
        accountRequest.setDebitCardDetail(debitCardDetailEntity);
        accountRequest.setNominee(nomineeEntity);
        accountRequest.setCustomerId(accountDto.getCustomerId());

        SavingAccountResponseDTO response = buildResponse(accountDto.getCustomerId());

        accountRequest.setResponsePayload(ow.writeValueAsString(response));
        accountRequest.setResponseTimestamp(LocalDateTime.now());
        accountRequest.setApplicationNo(response.getApplicationNo());
        accountRequest.setCustomerId(response.getCustomerId());
        accountRequest.setStatus(response.getStatus());
        repository.persist(accountRequest);
        return response;
    }

    private Address setAddress(Address addressEntity, com.apibanking.accountopening.savings.dto.Address address){
        addressEntity.setLine1(address.getLine1());
        addressEntity.setLine2(address.getLine2());
        addressEntity.setLine3(address.getLine3());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressEntity.setCountry(address.getCountry());
        addressEntity.setPinCode(address.getPinCode());
        return addressEntity;
    }

    private com.apibanking.accountopening.savings.entity.Contact setContact(
            com.apibanking.accountopening.savings.entity.Contact contactEntity, Contact contact) {
        contactEntity.setEmail(contact.getEmail());
        contactEntity.setResidenceTelephone(contact.getResidenceTelephone());
        contactEntity.setMobileNumber(contact.getMobileNumber());
        contactEntity.setMobileNumberServiceProvider(contact.getMobileNumberServiceProvider());
        contactEntity.setOfficeTelephone(contact.getOfficeTelephone());
        contactEntity.setInstaAlert(contact.isInstaAlert());
        return contactEntity;
    }

    private com.apibanking.accountopening.savings.entity.DebitCardDetail setDebitCardDetail(com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity, DebitCardDetail debitCardDto){
        debitCardDetailEntity.setCardType(debitCardDto.getCardType());
        debitCardDetailEntity.setOptForCard(debitCardDto.isOptForCard());
        return debitCardDetailEntity;
    }

    private com.apibanking.accountopening.savings.entity.Nominee setNominee(ModelMapper modelMapper, com.apibanking.accountopening.savings.entity.Nominee nomineeEntity, Nominee nomineeDto){
        nomineeEntity.setDateOfBirth(nomineeDto.getDateOfBirth());
        com.apibanking.accountopening.savings.dto.Address address = nomineeDto.getAddress();
        Address addressEntity = modelMapper.map(nomineeDto.getAddress(), Address.class);
        addressEntity.setLine1(address.getLine1());
        addressEntity.setLine2(address.getLine2());
        addressEntity.setLine3(address.getLine3());
        addressEntity.setCity(address.getCity());
        addressEntity.setState(address.getState());
        addressEntity.setCountry(address.getCountry());
        addressEntity.setPinCode(address.getPinCode());

        nomineeEntity.setAddress(addressEntity);

        nomineeEntity.setOptForNominee(nomineeDto.isOptForNominee());
        nomineeEntity.setRelationWithApplicant(nomineeDto.getRelationWithApplicant());
        nomineeEntity.setResidenceTelephone(nomineeDto.getResidenceTelephone());
        nomineeEntity.setName(nomineeDto.getName());
        nomineeEntity.setMobileNumber(nomineeDto.getMobileNumber());
        return nomineeEntity;
    } 

    private SavingAccountResponseDTO buildResponse(String customerId){
        return SavingAccountResponseDTO
        .builder()
        .customerId(customerId)
        .applicationNo(String.valueOf(new Random().nextInt(100000)))
        .status(AccountStatus.UnderInvestigation)
        .productCode("69869")
        .type(AccountType.Savings)
        .build();

    }
}
