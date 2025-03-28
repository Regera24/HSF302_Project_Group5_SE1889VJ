package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.entity.Account;
import org.group5.coolcafe.repository.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);
        return User.withUsername(account.getUsername())
                .password(account.getPassword())
                .roles(account.getRole().getCode())
                .build();
    }
}
