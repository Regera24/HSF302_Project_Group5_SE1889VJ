package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();
    Page<Category> findAll(Pageable pageable);
    @Query("SELECT c FROM Category c " +
            "WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:dateFrom IS NULL OR c.createdAt >= :dateFrom) " +
            "AND (:dateTo IS NULL OR c.createdAt <= :dateTo)")
    List<Category> findByName(@Param("name") String name,
                              @Param("dateFrom") LocalDateTime dateFrom,
                              @Param("dateTo") LocalDateTime dateTo);
    Category findById(long id);
}
