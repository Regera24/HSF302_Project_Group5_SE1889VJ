package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = """
            select p.id, p.name, p.sold_quantity
            from product p
        """, nativeQuery = true)
    List<Object[]> getProductRatio();
}
