package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.order.OrderCreationRequest;
import org.group5.coolcafe.dto.order.OrderDTO;
import org.group5.coolcafe.dto.order.OrderUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public void createOrder(OrderCreationRequest request);
    public void updateOrder(Long orderId, OrderUpdateRequest request);
    public List<OrderDTO> getOrders(int pageNo, int pageSize);
}
