package org.group5.coolcafe.Repository;

import org.group5.coolcafe.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
