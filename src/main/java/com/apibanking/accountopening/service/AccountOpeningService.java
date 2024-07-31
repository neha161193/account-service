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
import com.apibanking.account.service.AccountValidator;
import com.apibanking.accountopening.current.dto.CurrentAccountRequestDTO;
import com.apibanking.accountopening.fixeddeposit.dto.FixedDepositAccountRequestDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.dto.AccountStatus;
import com.apibanking.accountopening.savings.dto.AccountType;
import com.apibanking.accountopening.savings.dto.Contact;
import com.apibanking.accountopening.savings.dto.DebitCardDetail;
import com.apibanking.accountopening.savings.dto.Nominee;
import com.apibanking.accountopening.savings.dto.AccountOpeningRequestDTO;
import com.apibanking.accountopening.savings.dto.AccountOpeningResponseDTO;
import com.apibanking.accountopening.savings.dto.UpdateAccountOpeningStatusDTO;
import com.apibanking.accountopening.savings.entity.Address;
import com.apibanking.accountopening.savings.entity.AuthorizedSignatoryDetail;
import com.apibanking.accountopening.savings.entity.AccountOpeningRequest;
import com.apibanking.accountopening.savings.repository.SavingAccountRequestRepository;
import com.apibanking.exception.BusinessErrorException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class AccountOpeningService {
    @Inject
    SavingAccountRequestRepository repository;
    @Inject
    AccountRepository accountRepository;
    @Inject
    ModelMapper modelMapper;
    @Inject
    AccountValidator validator;

    @Transactional
    public AccountOpeningStatusDTO getAccount(String applicationNo) {
        AccountOpeningRequest account = repository.findByApplicationNo(applicationNo);
        AccountOpeningStatusDTO status = modelMapper.map(account, AccountOpeningStatusDTO.class);
        return status;
    }

    @Transactional
    public void updateAccount(UpdateAccountOpeningStatusDTO updateAccountStatusDto) {
        AccountOpeningRequest savingAccount = repository.findByApplicationNo(updateAccountStatusDto.getApplicationNo());
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
            for (AccountAddress accountAddress : accountAddresses) {
                accountAddress.setId(null);
                accountAddress.setAccount(account);
            }
            account.setAddress(accountAddresses);
            account.setId(null);

            if (savingAccount.getAccountAuthorizedSignatory() != null) {
                List<AccountAuthorizedSignatory> authorizedSignatoryList = modelMapper.map(
                        savingAccount.getAccountAuthorizedSignatory(),
                        new TypeToken<List<AccountAuthorizedSignatory>>() {
                        }.getType());
                for (AccountAuthorizedSignatory accountAuthorizedSignatory : authorizedSignatoryList) {
                    accountAuthorizedSignatory.setId(null);
                    accountAuthorizedSignatory.setAccount(account);
                    AccountAddress address = accountAuthorizedSignatory.getAddress();
                    accountAuthorizedSignatory.setAddress(address);
                    address.setId(null);
                    address.setAccountAuthorizedSignatory(accountAuthorizedSignatory);
                }
                account.setAccountAuthorizedSignatory(authorizedSignatoryList);
            }
            account.setId(null);

            AccountDebitCardDetail accountDebitCardDetail = modelMapper.map(savingAccount.getDebitCardDetail(),
                    AccountDebitCardDetail.class);
            accountDebitCardDetail.setId(null);
            accountDebitCardDetail.setAccount(account);
            account.setDebitCardDetail(accountDebitCardDetail);
            account.setAccountOpeningDate(LocalDate.now());
            account.setAccountHolderName(
                    savingAccount.getApplicantFirstName() + " " + savingAccount.getApplicantLastName());
            account.setAccountBalance(savingAccount.getRequiredAverageBalance());
            account.setInterestRate(updateAccountStatusDto.getInterestRate());
            accountRepository.persist(account);
        }
    }

    @Transactional
    public AccountOpeningResponseDTO openSavingAccount(AccountOpeningRequestDTO accountDto)
                    throws JsonProcessingException {
            AccountOpeningRequest accountOpeningRequestExist = repository.findByPanNo(
                            accountDto.getPanNo());
            if (accountOpeningRequestExist != null) {
                    throw new BusinessErrorException("This Account Opening Request already exist with status "
                                    + accountOpeningRequestExist.getStatus() + " for account type "
                                    + accountOpeningRequestExist.getType(), Status.BAD_REQUEST);
            }

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
            com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = modelMapper
                            .map(
                                            debitCardDto,
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
            accountRequest.setApplicantFirstName(accountDto.getApplicant().getFirstName());
            accountRequest.setApplicantLastName(accountDto.getApplicant().getLastName());
            accountRequest.setRequestPayload(ow.writeValueAsString(accountDto));
            accountRequest.setContact(contactEntity);
            accountRequest.setDebitCardDetail(debitCardDetailEntity);
            accountRequest.setNominee(nomineeEntity);
            accountRequest.setAddress(addressList);

            AccountOpeningResponseDTO response = buildResponse(accountDto.getCustomerId(),
                            accountDto.getType());
            accountRequest.setResponsePayload(ow.writeValueAsString(response));
            accountRequest.setResponseTimestamp(LocalDateTime.now());
            accountRequest.setApplicationNo(response.getApplicationNo());
            accountRequest.setCustomerId(response.getCustomerId());
            accountRequest.setStatus(response.getStatus());
            accountRequest.setId(null);
            repository.persist(accountRequest);
            return response;
    }

    private AccountOpeningResponseDTO buildResponse(String customerId, AccountType type) {
        AccountOpeningResponseDTO response = new AccountOpeningResponseDTO();
        response.setCustomerId(customerId);
        response.setApplicationNo(String.valueOf(new Random().nextInt(100000)));
        response.setStatus(AccountStatus.UnderInvestigation);
        response.setProductCode("69869");
        response.setType(type);
        return response;
    }

    @Transactional
    public AccountOpeningResponseDTO openCurrentAccount(@Valid CurrentAccountRequestDTO accountDto)
                    throws JsonProcessingException {

            AccountOpeningRequest accountOpeningRequestExist = repository.findByPanNo(
                            accountDto.getPanNo());
            if (accountOpeningRequestExist != null) {
                    throw new BusinessErrorException("This Account Opening Request already exist with status "
                                    + accountOpeningRequestExist.getStatus() + " for account type "
                                    + accountOpeningRequestExist.getType(), Status.BAD_REQUEST);
            }
            AccountOpeningRequest accountRequest = modelMapper.map(accountDto, AccountOpeningRequest.class);

            List<Address> addressList = new ArrayList<>();
            for (com.apibanking.accountopening.savings.dto.Address address : accountDto.getAddress()) {
                    Address addressEntity = modelMapper.map(address, Address.class);
                    addressEntity.setAccountOpeningRequest(accountRequest);
                    addressList.add(addressEntity);
            }

            List<AuthorizedSignatoryDetail> authorizedSignatoryDetailList = new ArrayList<>();
            for (com.apibanking.accountopening.current.dto.AuthorizedSignatoryDetail authorizedSignatoryDetail : accountDto
                            .getAccountAuthorizedSignatory()) {
                    AuthorizedSignatoryDetail authorizedSignatoryDetailEntity = modelMapper.map(
                                    authorizedSignatoryDetail,
                                    AuthorizedSignatoryDetail.class);
                    authorizedSignatoryDetailEntity
                                    .setApplicantFirstName(authorizedSignatoryDetail.getApplicant().getFirstName());
                    authorizedSignatoryDetailEntity
                                    .setApplicantLastName(authorizedSignatoryDetail.getApplicant().getLastName());
                    authorizedSignatoryDetailEntity
                                    .setApplicantMiddleName(authorizedSignatoryDetail.getApplicant().getMiddleName());
                    authorizedSignatoryDetailEntity.setAccountOpeningRequest(accountRequest);
                    authorizedSignatoryDetailEntity.getAddress()
                                    .setAuthorizedSignatoryDetail(authorizedSignatoryDetailEntity);
                    authorizedSignatoryDetailList.add(authorizedSignatoryDetailEntity);
            }

            com.apibanking.accountopening.current.dto.Contact contact = accountDto.getContact();
            com.apibanking.accountopening.savings.entity.Contact contactEntity = modelMapper.map(contact,
                            com.apibanking.accountopening.savings.entity.Contact.class);
            contactEntity.setOfficeTelephone(contact.getTelephoneNumber1());
            contactEntity.setResidenceTelephone(contact.getTelephoneNumber2());
            contactEntity.setAccountOpeningRequest(accountRequest);

            DebitCardDetail debitCardDto = accountDto.getDebitCardDetail();
            com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetailEntity = modelMapper.map(
                            debitCardDto,
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
            accountRequest.setApplicantFirstName(accountDto.getApplicant().getFirstName());
            accountRequest.setApplicantLastName(accountDto.getApplicant().getLastName());
            accountRequest.setRequestPayload(ow.writeValueAsString(accountDto));
            accountRequest.setContact(contactEntity);
            accountRequest.setDebitCardDetail(debitCardDetailEntity);
            accountRequest.setNominee(nomineeEntity);
            accountRequest.setAddress(addressList);
            accountRequest.setAccountAuthorizedSignatory(authorizedSignatoryDetailList);

            AccountOpeningResponseDTO response = buildResponse(accountDto.getCustomerId(), accountDto.getType());
            accountRequest.setResponsePayload(ow.writeValueAsString(response));
            accountRequest.setResponseTimestamp(LocalDateTime.now());
            accountRequest.setApplicationNo(response.getApplicationNo());
            accountRequest.setCustomerId(response.getCustomerId());
            accountRequest.setStatus(response.getStatus());
            accountRequest.setId(null);
            repository.persist(accountRequest);
            return response;
    }

    @Transactional
    public AccountOpeningResponseDTO openFixedDepositAccount(@Valid FixedDepositAccountRequestDTO accountDto)
            throws JsonProcessingException {
        Account account = validator.validateCustomerIdAndAccountNo(accountDto.getCustomerId(),
                accountDto.getInstruction().getDebitAccountNumber());   
        if ((account.getAccountBalance().compareTo(accountDto.getInstruction().getAmount()) >= 0)
                ||
                (accountDto.getPaymentDetail().getAmount().compareTo(accountDto.getInstruction().getAmount()) >= 0)) {
            AccountOpeningRequest accountRequest = modelMapper.map(accountDto, AccountOpeningRequest.class);
            ObjectMapper om = new ObjectMapper();
            om = JsonMapper.builder()
                    .addModule(new JavaTimeModule())
                    .build();
            ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
            accountRequest.setApplicantFirstName(accountDto.getApplicant().getFirstName());
            accountRequest.setApplicantLastName(accountDto.getApplicant().getLastName());
            accountRequest.setRequestPayload(ow.writeValueAsString(accountDto));

            AccountOpeningResponseDTO response = buildResponse(accountDto.getCustomerId(), accountDto.getType());
            accountRequest.setResponsePayload(ow.writeValueAsString(response));
            accountRequest.setResponseTimestamp(LocalDateTime.now());
            accountRequest.setApplicationNo(response.getApplicationNo());
            accountRequest.setCustomerId(response.getCustomerId());
            accountRequest.setStatus(response.getStatus());

            List<Address> addresses = modelMapper.map(account.getAddress(),
                    new TypeToken<List<Address>>() {
                    }.getType());

            for (Address address : addresses) {
                address.setId(null);
                address.setAccountOpeningRequest(accountRequest);
            }
            accountRequest.setAddress(addresses);
            com.apibanking.accountopening.savings.entity.Contact contact = modelMapper.map(account.getContact(),
                    com.apibanking.accountopening.savings.entity.Contact.class);
            contact.setId(null);
            contact.setAccountOpeningRequest(accountRequest);
            accountRequest.setContact(contact);
            com.apibanking.accountopening.savings.entity.Nominee nominee = modelMapper.map(account.getNomineeDetail(),
                    com.apibanking.accountopening.savings.entity.Nominee.class);
            nominee.setId(null);
            nominee.setAccountOpeningRequest(accountRequest);
            nominee.getAddress().setId(null);
            nominee.getAddress().setNominee(nominee);
            accountRequest.setNominee(nominee);
            accountRequest.setId(null);
            accountRequest.setRequiredAverageBalance(accountDto.getInstruction().getAmount());
            accountRequest.setPanNo(account.getPanNo());
            com.apibanking.accountopening.savings.entity.DebitCardDetail debitCardDetail = modelMapper.map(
                account.getDebitCardDetail(),
                    com.apibanking.accountopening.savings.entity.DebitCardDetail.class);
            debitCardDetail.setId(null);
            debitCardDetail.setAccountOpeningRequest(accountRequest);
            accountRequest.setDebitCardDetail(debitCardDetail);
            repository.persist(accountRequest);
            return response;
        }
        return null;
    }
}
