package org.group5.coolcafe.dto.order;

import lombok.*;
import org.group5.coolcafe.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long tableId;
    private String tableCode;
    private String status;
    private Double totalAmount;
    private List<OrderDetail> orderDetails;
}
