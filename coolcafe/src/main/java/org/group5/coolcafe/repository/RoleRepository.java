package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public Optional<Role> getByCode(String code);
}
