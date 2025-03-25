package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.OrderDetailConverter;
import org.group5.coolcafe.dto.order.OrderDetail;
import org.group5.coolcafe.repository.OrderDetailRepository;
import org.group5.coolcafe.service.OrderDetailService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderDetailConverter orderDetailConverter;

    @Override
    public List<OrderDetail> getOrderDetailsByOrder(Long orderId) {
        return orderDetailRepository.findByOrder_Id(orderId).stream().map(orderDetailConverter::toOrderDetail).collect(Collectors.toList());
    }
}
