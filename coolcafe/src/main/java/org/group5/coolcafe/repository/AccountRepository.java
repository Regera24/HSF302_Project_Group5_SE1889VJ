package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
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
}
