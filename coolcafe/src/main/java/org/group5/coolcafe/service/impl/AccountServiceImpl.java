package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.AccountConverter;
import org.group5.coolcafe.dto.AccountDTO;
import org.group5.coolcafe.entity.Account;
import org.group5.coolcafe.repository.AccountRepository;
import org.group5.coolcafe.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;

    @Override
    public List<AccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountConverter::convertAccountToAccountDTO).collect(Collectors.toList());
    }

    @Override
    public void createAccount(AccountDTO accountDTO) {
        Account account = accountConverter.convertAccountDTOToAccount(accountDTO);
        accountRepository.save(account);
    }

    @Override
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.getAccountsById(id);
        account.setIsActive(false);
        accountRepository.save(account);
    }
}
