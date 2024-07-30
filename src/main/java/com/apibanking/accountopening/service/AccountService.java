package com.apibanking.accountopening.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.apibanking.account.dto.AccountDTO;
import com.apibanking.account.entity.Account;
import com.apibanking.account.entity.AccountAddress;
import com.apibanking.account.repository.AccountAddressRepository;
import com.apibanking.account.repository.AccountContactRepository;
import com.apibanking.account.repository.AccountNomineeRepository;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.exception.BusinessErrorException;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response.Status;

@ApplicationScoped
public class AccountService {
    @Inject
    AccountRepository accountRepository;
    @Inject
    AccountAddressRepository accountAddressRepository;
    @Inject
    AccountContactRepository accountContactRepository;
    @Inject
    AccountNomineeRepository accountNomineeRepository;
    @Inject
    ModelMapper modelMapper;

    @Transactional
    public List<AccountDTO> getAllAccounts(String customerId, String accountNo) {
        List<Account> accounts = new ArrayList<Account>();
        if (accountNo != null) {
            try{
                Account account = accountRepository.findByCustomerIdAndAccountNo(customerId, accountNo);
                accounts.add(account);
            }
            catch(NoResultException ex){
                throw new BusinessErrorException("No Record Found for customerId " + customerId + " and accountNo " + accountNo, 
                    Status.NOT_FOUND);
            }
        } else {
            accounts = accountRepository.findByCustomerId(customerId);
        }
        List<AccountDTO> accountDTOList = modelMapper.map(accounts,
                new TypeToken<List<AccountDTO>>() {
                }.getType());
        return accountDTOList;
    }
    
    @Transactional
    public AccountDTO updateAccount(AccountDTO accountDTO) {
        Account account = accountRepository.findByCustomerIdAndAccountNo(accountDTO.getCustomerId(),
                accountDTO.getAccountNo());
        
        if (account != null) {
            if (accountDTO.getAddress() != null) {
                for (AccountAddress address : account.getAddress()) {
                    accountAddressRepository.deleteById(address.getId());
                }
                List<AccountAddress> accountAddress = modelMapper.map(accountDTO.getAddress(),
                        new TypeToken<List<AccountAddress>>() {
                        }.getType());
                for (AccountAddress aaccountAddress : accountAddress) {
                    aaccountAddress.setAccount(account);
                }
                account.setAddress(accountAddress);
            }

            if (accountDTO.getContact() != null) {
                account.getContact().setEmail(accountDTO.getContact().getEmail());
                account.getContact().setMobileNumber(accountDTO.getContact().getMobileNumber());
                account.getContact().setInstaAlert(accountDTO.getContact().isInstaAlert());
                account.getContact().setMobileNumberServiceProvider(accountDTO.getContact().getMobileNumberServiceProvider());
                account.getContact().setOfficeTelephone(accountDTO.getContact().getOfficeTelephone());
                account.getContact().setResidenceTelephone(accountDTO.getContact().getResidenceTelephone());
            }
            if (accountDTO.getNomineeDetail() != null) {
                account.getNomineeDetail().setDateOfBirth(accountDTO.getNomineeDetail().getDateOfBirth());
                account.getNomineeDetail().setMobileNumber(accountDTO.getNomineeDetail().getMobileNumber());
                account.getNomineeDetail().setName(accountDTO.getNomineeDetail().getName());
                account.getNomineeDetail().setOptForNominee(accountDTO.getNomineeDetail().isOptForNominee());
                account.getNomineeDetail().setRelationWithApplicant(accountDTO.getNomineeDetail().getRelationWithApplicant());
                account.getNomineeDetail().setResidenceTelephone(accountDTO.getNomineeDetail().getResidenceTelephone());
                account.getNomineeDetail().getAddress().setLine1(accountDTO.getNomineeDetail().getAddress().getLine1());
                account.getNomineeDetail().getAddress().setLine2(accountDTO.getNomineeDetail().getAddress().getLine2());
                account.getNomineeDetail().getAddress().setLine3(accountDTO.getNomineeDetail().getAddress().getLine3());   
                account.getNomineeDetail().getAddress().setPinCode(accountDTO.getNomineeDetail().getAddress().getPinCode());
                account.getNomineeDetail().getAddress().setState(accountDTO.getNomineeDetail().getAddress().getState());
                account.getNomineeDetail().getAddress().setAddressType(accountDTO.getNomineeDetail().getAddress().getAddressType());
                account.getNomineeDetail().getAddress().setCountry(accountDTO.getNomineeDetail().getAddress().getCountry());
                account.getNomineeDetail().getAddress().setCity(accountDTO.getNomineeDetail().getAddress().getCity());
            }
            if (accountDTO.getStatus() != null) {
                account.setStatus(accountDTO.getStatus());
            }
            accountRepository.persist(account);
        }
        return accountDTO;
    }
 }
