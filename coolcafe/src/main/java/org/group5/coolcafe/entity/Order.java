package org.group5.coolcafe.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.group5.coolcafe.enums.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Orders")
public class Order extends AbstractEntity {
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    OrderStatus status;

    @Column(name = "totalAmount")
    Double totalAmount;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "AccountID")
    Account account;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    ServeTable table;

    @OneToMany(mappedBy = "order",cascade = { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    List<HasOrderDetail> orderDetails = new ArrayList<>();
}
