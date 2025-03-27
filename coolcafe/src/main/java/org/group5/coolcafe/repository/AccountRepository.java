package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public List<Account> getAccountByIsActive(boolean isActive);
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
    public boolean existsByPhoneNumber(String phoneNumber);
    public Optional<Account> getAccountByUsername(String username);
    public Optional<Account> getAccountByToken(String token);

    @Query(value = """
            select count(*) from account a where a.role_id = 3
        """, nativeQuery = true)
    Long getCustomerNumber();

    @Query(value = """
            select count(*) from account a where a.role_id = 2
        """, nativeQuery = true)
    Long getEmployeeNumber();

    Account getAccountsById(Long id);

    Account findByUsername(String username);

    Page<Account> findAccountsByIsActive(Boolean isActive, Pageable pageable);

    Page<Account> findAccountByUsernameContainingAndIsActive(String keyword, Pageable pageable, boolean isActive);

    Account findByPhoneNumber(String phoneNumber);
}
