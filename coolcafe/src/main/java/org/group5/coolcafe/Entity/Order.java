package org.group5.coolcafe.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @Column(name = "status")
    String status;

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
