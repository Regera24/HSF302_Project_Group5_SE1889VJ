package org.group5.coolcafe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "HasOrderDetail")
public class HasOrderDetail extends AbstractEntity {
    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "productPrice")
    Double productPrice;

    @Column(name = "productName")
    String productName;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "orderID")
    Order order;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinColumn(name = "productID")
    Product product;
}
