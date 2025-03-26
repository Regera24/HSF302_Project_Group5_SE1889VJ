package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.order.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderDetailService {
    public List<OrderDetail> getOrderDetailsByOrder(Long orderId);
}
