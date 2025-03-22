package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.AccountConverter;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.group5.coolcafe.entity.Account;
import org.group5.coolcafe.entity.Role;
import org.group5.coolcafe.enums.RoleEnum;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.AccountRepository;
import org.group5.coolcafe.repository.RoleRepository;
import org.group5.coolcafe.service.AccountService;
import org.group5.coolcafe.utils.Mail;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final RoleRepository roleRepository;
    private final Mail mail;

    @Override
    public void createAccount(AccountCreationRequest request) {
        Account account = accountConverter.toAccountEntity(request);

        if(StringUtils.hasLength(request.getEmail().trim()) && accountRepository.existsByEmail(request.getEmail().trim().toLowerCase())){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }else if(!StringUtils.hasLength(request.getEmail().trim())){
            account.setEmail(null);
        }
        if(accountRepository.existsByUsername(request.getUsername().trim().toLowerCase())){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if(accountRepository.existsByPhoneNumber(request.getPhoneNumber().trim().toLowerCase())){
            throw new AppException(ErrorCode.PHONENUMBER_EXISTED);
        }

        Role customerRole = roleRepository.getByCode(RoleEnum.CUSTOMER.toString()).orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        account.setRole(customerRole);
        account.setIsActive(true);

        accountRepository.save(account);
    }

    @Override
    public void sendEmail(String username) {
        Account account = accountRepository.getAccountByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        if(StringUtils.hasLength(account.getEmail())){
            String code = generate6DigitCode();
            account.setToken(code);
            accountRepository.save(account);
            mail.sendEmail(account.getEmail(), "http://localhost:8080/reset-password?token="+code);
        }else{
            throw new AppException(ErrorCode.NOT_FOUND);
        }
    }

    @Override
    public void setNewPassword(String token, String newPassword) {
        Account account = accountRepository.getAccountByToken(token).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        account.setPassword(newPassword);
        accountRepository.save(account);
    }

    @Override
    public Long getNumberOfEmployee() {
        return accountRepository.getEmployeeNumber();
    }

    @Override
    public Long getNumberOfCustomer() {
        return accountRepository.getCustomerNumber();
    }

    private String generate6DigitCode() {
        SecureRandom random = new SecureRandom();
        return String.format("%06d0", random.nextInt(1000000));
    }
}
