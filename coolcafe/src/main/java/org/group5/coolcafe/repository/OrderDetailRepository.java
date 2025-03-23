package org.group5.coolcafe.repository;

import org.group5.coolcafe.entity.HasOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderDetailRepository extends JpaRepository<HasOrderDetail, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM HasOrderDetail h WHERE h.order.id = :orderId")
    void deleteByOrderId(@Param("orderId") Long orderId);
}
