package org.group5.coolcafe.dto.order;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    private Long productId;
    private Integer quantity;
    private String productName;
    private String productImage;
    private Double productPrice;
}
