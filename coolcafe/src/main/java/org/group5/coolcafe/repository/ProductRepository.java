package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Category;
import org.group5.coolcafe.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Product findById(long id);
    @Query("SELECT c FROM Product c " +
            "WHERE (:name IS NULL OR LOWER(c.name) " +
            "LIKE LOWER(CONCAT('%', :name, '%')))")
    Page<Product> findByName(String name, Pageable pageable);
    @Query("SELECT c FROM Product c " +
            "WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:categoryId IS NULL OR c.category.id = :categoryId)")
    Page<Product> findByNameAndCategory(@Param("name") String name,
                                        @Param("categoryId") Long categoryId,
                                        Pageable pageable);
}
