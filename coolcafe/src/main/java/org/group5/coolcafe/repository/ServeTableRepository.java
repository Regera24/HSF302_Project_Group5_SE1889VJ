package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.ServeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServeTableRepository extends JpaRepository<ServeTable, Long> {
}
