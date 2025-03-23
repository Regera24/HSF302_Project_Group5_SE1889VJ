package org.group5.coolcafe.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreationRequest {
    private List<OrderDetail> orderDetails;
    private Long tableId;
}
