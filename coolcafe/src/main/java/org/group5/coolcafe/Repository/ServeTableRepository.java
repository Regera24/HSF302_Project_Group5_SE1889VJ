package org.group5.coolcafe.Repository;

import org.group5.coolcafe.Entity.ServeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServeTableRepository extends JpaRepository<ServeTable, Long> {
}
