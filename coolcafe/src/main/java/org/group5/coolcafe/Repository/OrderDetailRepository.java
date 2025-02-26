package org.group5.coolcafe.Repository;

import org.group5.coolcafe.Entity.HasOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<HasOrderDetail, Long> {
}
