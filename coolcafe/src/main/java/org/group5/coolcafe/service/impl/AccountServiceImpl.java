package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.AccountConverter;
import org.group5.coolcafe.dto.manage_account.ManageAccountDTO;
import org.group5.coolcafe.dto.request.AccountCreationRequest;
import org.group5.coolcafe.entity.Account;
import org.group5.coolcafe.entity.Role;
import org.group5.coolcafe.enums.RoleEnum;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.AccountRepository;
import org.group5.coolcafe.repository.RoleRepository;
import org.group5.coolcafe.service.AccountService;
import org.group5.coolcafe.utils.CloudinaryService;
import org.group5.coolcafe.utils.Mail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountConverter accountConverter;
    private final RoleRepository roleRepository;
    private final CloudinaryService cloudinaryService;
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
        account.setToken(null);
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

    @Override
    public List<ManageAccountDTO> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map(accountConverter::convertAccountToAccountDTO).collect(Collectors.toList());
    }

    @Override
    public void createAccount(ManageAccountDTO accountDTO) {
        Account existingAccount = accountRepository.findByUsername(accountDTO.getUsername());
        Account account = accountRepository.findByPhoneNumber(accountDTO.getPhoneNumber());
        if (existingAccount != null) throw new AppException(ErrorCode.USERNAME_EXISTED);
        if (account != null) throw new AppException(ErrorCode.PHONENUMBER_EXISTED);
        Account accountOK = accountConverter.convertAccountDTOToAccount(accountDTO);
        accountOK.setIsActive(true);
        accountRepository.save(accountOK);
    }

    @Override
    public void updateAccount(String id,ManageAccountDTO accountDTO) {
        if (accountDTO.getPassword() == null || accountDTO.getPassword().isEmpty()) throw new AppException(ErrorCode.PASSWORD_INVALID);
        Account account = accountRepository.getAccountsById(Long.parseLong(id));
        Optional<Role> role = roleRepository.findById(Long.parseLong(accountDTO.getRole()));
        role.ifPresent(account::setRole);
        account.setPassword(accountDTO.getPassword());
        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.getAccountsById(id);
        account.setIsActive(false);
        accountRepository.save(account);
    }

    @Override
    public void updateProfile(String username,ManageAccountDTO accountDTO) throws IOException {
        Account account = accountRepository.findByUsername(username);
        String avatar = cloudinaryService.uploadFile(accountDTO.getAvatar());
        if (accountDTO.getName() == null || accountDTO.getName().isEmpty() || accountDTO.getName().length()<3){
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        if (accountDTO.getEmail() == null || accountDTO.getEmail().isEmpty()){
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        if (accountDTO.getPhoneNumber() == null || accountDTO.getPhoneNumber().isEmpty()){
            throw new AppException(ErrorCode.PHONENUMBER_EXISTED);
        }
        account.setName(accountDTO.getName());
        account.setEmail(accountDTO.getEmail());
        account.setDateOfBirth(LocalDate.parse(accountDTO.getDob()));
        account.setPhoneNumber(accountDTO.getPhoneNumber());
        account.setAvatar(avatar);
        accountRepository.save(account);
    }

    @Override
    public Page<ManageAccountDTO> getAllAccounts(String keyword, int page, int size, String sortField, String sortDirection) {
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortField).ascending()
                : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Account> accountPage = null;
        if (keyword == null || keyword.isEmpty()) {
            accountPage = accountRepository.findAll(pageable);
        } accountPage = accountRepository.findAccountByUsernameContaining(keyword, pageable);
        return accountPage.map(accountConverter::convertAccountToAccountDTO);
    }
}
