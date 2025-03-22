package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    public void createAccount(AccountCreationRequest request);
    public void sendEmail(String username);
    public void setNewPassword(String token, String newPassword);
    public Long getNumberOfEmployee();
    public Long getNumberOfCustomer();
}
