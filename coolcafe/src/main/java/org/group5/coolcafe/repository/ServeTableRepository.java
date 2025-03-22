package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.ServeTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServeTableRepository extends JpaRepository<ServeTable, Long> {

    @Query("SELECT st FROM ServeTable st WHERE st.id NOT IN :ids")
    Page<ServeTable> findByIdNotIn(@Param("ids") List<Long> ids, Pageable pageable);

    Optional<ServeTable> findByCode(String code);
}
