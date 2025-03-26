package org.group5.coolcafe.service;

import org.aspectj.weaver.ast.Or;
import org.group5.coolcafe.dto.order.OrderCreationRequest;
import org.group5.coolcafe.dto.order.OrderDTO;
import org.group5.coolcafe.dto.order.OrderUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    public OrderDTO getOrder(Long orderId);
    public void createOrder(OrderCreationRequest request);
    public void updateOrder(Long orderId, String status);
    public List<OrderDTO> getOrders(int pageNo, int pageSize);
    public int getTotalPages(int pageSize);
}
