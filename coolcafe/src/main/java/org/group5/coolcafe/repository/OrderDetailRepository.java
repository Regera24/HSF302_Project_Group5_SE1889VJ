package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.HasOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<HasOrderDetail, Long> {
}
