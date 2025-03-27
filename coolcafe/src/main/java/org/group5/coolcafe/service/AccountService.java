package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.manage_account.ManageAccountDTO;
import org.springframework.data.domain.Page;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface AccountService {
    public void createAccount(AccountCreationRequest request);
    public void sendEmail(String username);
    public void setNewPassword(String token, String newPassword);
    public Long getNumberOfEmployee();
    public Long getNumberOfCustomer();
    public ManageAccountDTO getAccountByUsername(String username);
    public List<ManageAccountDTO> getAllAccounts();
    public void createAccount(ManageAccountDTO manageAccountDTO);
    public void updateAccount(String id, ManageAccountDTO manageAccountDTO);
    public void deleteAccount(Long id);
    public void updateProfile(String username, ManageAccountDTO manageAccountDTO) throws IOException;
    public Page<ManageAccountDTO> getAllAccounts(String keyword, int page, int size, String sortFiled, String sortDirection);
}
