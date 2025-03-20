package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.AccountDTO;
import org.group5.coolcafe.entity.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AccountService {

    public List<AccountDTO> getAllAccounts();
    public void createAccount(AccountDTO accountDTO);
    public void updateAccount(Account account);
    public void deleteAccount(Long id);

}
