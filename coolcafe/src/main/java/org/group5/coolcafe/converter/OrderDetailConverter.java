package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.order.OrderDetail;
import org.group5.coolcafe.entity.HasOrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderDetailConverter {
    private final ModelMapper modelMapper;

    public OrderDetail toOrderDetail(HasOrderDetail hasOrderDetail) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductName(hasOrderDetail.getProduct().getName());
        orderDetail.setProductPrice(hasOrderDetail.getProduct().getPrice());
        orderDetail.setProductId(hasOrderDetail.getProduct().getId());
        orderDetail.setProductImage(hasOrderDetail.getProduct().getImage());
        orderDetail.setQuantity(hasOrderDetail.getQuantity());
        return orderDetail;
    }
}
