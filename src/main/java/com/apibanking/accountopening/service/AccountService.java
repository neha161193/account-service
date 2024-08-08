package com.apibanking.accountopening.service;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import com.apibanking.account.dto.AccountDTO;
import com.apibanking.account.entity.Account;
import com.apibanking.account.entity.AccountAddress;
import com.apibanking.account.repository.AccountAddressRepository;
import com.apibanking.account.repository.AccountRepository;
import com.apibanking.account.service.AccountValidator;

import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class AccountService {
    @Inject
    AccountRepository accountRepository;
    @Inject
    AccountValidator validator;
    @Inject
    AccountAddressRepository accountAddressRepository;
    @Inject
    ModelMapper modelMapper;

    @WithTransaction
    public Uni<List<AccountDTO>> getAllAccounts(String customerId, String accountNo) {
        // Determine whether to use validator or repository based on accountNo
        Uni<List<Account>> accounts = (accountNo != null)
                ? validator.validateCustomerIdAndAccountNo(customerId, accountNo).onItem().transformToUni(
                        result -> Uni.createFrom().item(Optional.ofNullable(result).map(List::of).orElse(List.of())))
                : accountRepository.findByCustomerId(customerId);

        // Map the result to DTOs
        return accounts
                .onItem().transform(accountsList -> modelMapper.map(accountsList, new TypeToken<List<AccountDTO>>() {
                }.getType()));
    }

    @WithTransaction
    public Uni<AccountDTO> updateAccount(AccountDTO accountDTO) {
        return validator.validateCustomerIdAndAccountNo(accountDTO.getCustomerId(), accountDTO.getAccountNo())
                .onItem().transformToUni(account -> {
                        if (accountDTO.getAddress() != null) {
                                // Delete existing addresses
                                Uni<Void> deleteAddressesUni = Uni.createFrom().item(account.getAddress())
                                    .onItem().transformToUni(addresses -> {
                                        // Delete addresses in parallel
                                        return Uni.combine().all().unis(
                                            addresses.stream()
                                                .map(address -> accountAddressRepository.deleteById(address.getId()))
                                                .toArray(Uni[]::new)
                                        ).discardItems(); // Discard the results as we only need to ensure deletion
                                    });
                
                                // Map new addresses
                                List<AccountAddress> newAddresses = modelMapper.map(accountDTO.getAddress(),
                                        new TypeToken<List<AccountAddress>>() {
                                        }.getType());
                                newAddresses.forEach(address -> address.setAccount(account));
                                account.setAddress(newAddresses);
                
                                return deleteAddressesUni.onItem().transformToUni(ignored -> {
                                    // Continue with other updates after addresses are processed
                                    return updateAccountDetails(account, accountDTO);
                                });
                            } else {
                                return updateAccountDetails(account, accountDTO);
                            }
                });
    }
    private Uni<AccountDTO> updateAccountDetails(Account account, AccountDTO accountDTO) {
        if (accountDTO.getContact() != null) {
                account.getContact().setEmail(accountDTO.getContact().getEmail());
                account.getContact().setMobileNumber(accountDTO.getContact().getMobileNumber());
                account.getContact().setInstaAlert(accountDTO.getContact().isInstaAlert());
                account.getContact()
                        .setMobileNumberServiceProvider(
                                accountDTO.getContact().getMobileNumberServiceProvider());
                account.getContact().setOfficeTelephone(accountDTO.getContact().getOfficeTelephone());
                account.getContact().setResidenceTelephone(accountDTO.getContact().getResidenceTelephone());
            }
            if (accountDTO.getNomineeDetail() != null) {
                account.getNomineeDetail().setDateOfBirth(accountDTO.getNomineeDetail().getDateOfBirth());
                account.getNomineeDetail().setMobileNumber(accountDTO.getNomineeDetail().getMobileNumber());
                account.getNomineeDetail().setName(accountDTO.getNomineeDetail().getName());
                account.getNomineeDetail().setOptForNominee(accountDTO.getNomineeDetail().isOptForNominee());
                account.getNomineeDetail()
                        .setRelationWithApplicant(accountDTO.getNomineeDetail().getRelationWithApplicant());
                account.getNomineeDetail()
                        .setResidenceTelephone(accountDTO.getNomineeDetail().getResidenceTelephone());
                account.getNomineeDetail().getAddress()
                        .setLine1(accountDTO.getNomineeDetail().getAddress().getLine1());
                account.getNomineeDetail().getAddress()
                        .setLine2(accountDTO.getNomineeDetail().getAddress().getLine2());
                account.getNomineeDetail().getAddress()
                        .setLine3(accountDTO.getNomineeDetail().getAddress().getLine3());
                account.getNomineeDetail().getAddress()
                        .setPinCode(accountDTO.getNomineeDetail().getAddress().getPinCode());
                account.getNomineeDetail().getAddress()
                        .setState(accountDTO.getNomineeDetail().getAddress().getState());
                account.getNomineeDetail().getAddress()
                        .setAddressType(accountDTO.getNomineeDetail().getAddress().getAddressType());
                account.getNomineeDetail().getAddress()
                        .setCountry(accountDTO.getNomineeDetail().getAddress().getCountry());
                account.getNomineeDetail().getAddress()
                        .setCity(accountDTO.getNomineeDetail().getAddress().getCity());
            }
            if (accountDTO.getStatus() != null) {
                account.setStatus(accountDTO.getStatus());
            }
            return accountRepository.persist(account)
        .onItem().transformToUni(ignored -> Uni.createFrom().item(accountDTO));
    }
}
