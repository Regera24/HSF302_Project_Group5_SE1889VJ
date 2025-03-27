package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.order.OrderDTO;
import org.group5.coolcafe.dto.order.OrderDetail;
import org.group5.coolcafe.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final ModelMapper modelMapper;
    private final OrderDetailConverter orderDetailConverter;

    public OrderDTO toOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setStatus(order.getStatus().toString());
        orderDTO.setTotalAmount(order.getTotalAmount());
        if(order.getCreatedAt() != null ) {
            orderDTO.setCreatedAt(order.getCreatedAt());
        }
        if(order.getUpdatedAt() != null) {
            orderDTO.setUpdatedAt(order.getUpdatedAt());
        }
        orderDTO.setId(order.getId());
        if(order.getTable() != null) {
            orderDTO.setTableCode(order.getTable().getCode());
            orderDTO.setTableId(order.getTable().getId());
        }
        orderDTO.setOrderDetails(order.getOrderDetails().stream().map(orderDetailConverter::toOrderDetail).collect(Collectors.toList()));
        return orderDTO;
    }
}
