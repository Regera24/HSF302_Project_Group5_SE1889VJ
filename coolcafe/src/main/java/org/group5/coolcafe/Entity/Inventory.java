package org.group5.coolcafe.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "Inventory")
public class Inventory extends AbstractEntity {
    @Column(name = "name")
    String name;

    @Column(name = "supplier")
    String supplier;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "description")
    String description;

    @Column(name = "expiredAt")
    LocalDate expiredAt;

}
