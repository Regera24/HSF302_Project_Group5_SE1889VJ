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
@Table(name = "Product")
public class Product extends AbstractEntity {
    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @Column(name = "price")
    Double price;

    @Column(name = "soldQuantity")
    Long soldQuantity;

    @Column(name = "image")
    String image;

    @Column(name = "estimatedQuantity")
    Long estimatedQuantity;

    @Column(name = "rate")
    Integer rate;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "categoryID")
    Category category;

    @OneToMany(mappedBy = "product",cascade = { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    List<HasOrderDetail> orderDetails = new ArrayList<>();
}
