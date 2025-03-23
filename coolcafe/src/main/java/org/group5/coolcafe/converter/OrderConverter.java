package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.order.OrderDTO;
import org.group5.coolcafe.dto.order.OrderDetail;
import org.group5.coolcafe.entity.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final ModelMapper modelMapper;

    public OrderDTO toOrderDTO(Order order){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setId(order.getId());
        orderDTO.setTotalAmount(order.getTotalAmount());
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setUpdatedAt(order.getUpdatedAt());
        orderDTO.setStatus(order.getStatus());
//
//        orderDTO.setTableId(order.getTable().getId());
//        orderDTO.setTableCode(order.getTable().getCode());

        List<OrderDetail> orderDetailList = order.getOrderDetails().stream().map((item) -> {
            return OrderDetail.builder()
                    .quantity(item.getQuantity())
                    .productName(item.getProductName())
                    .productPrice(item.getProductPrice())
                    .build();
        }).toList();

        orderDTO.setOrderDetails(orderDetailList);

        return orderDTO;
    }
}
