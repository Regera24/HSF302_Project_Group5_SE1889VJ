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
@Table(name = "ServeTable")
public class ServeTable extends AbstractEntity{
    @Column(name = "code")
    String code;

    @Column(name = "description")
    String description;

    @Column(name = "location")
    String location;

    @Column(name = "isActive")
    Boolean isActive;

    @OneToMany(mappedBy = "table",cascade = { CascadeType.PERSIST, CascadeType.MERGE},fetch = FetchType.LAZY)
    List<ReserveTable> reserveTables = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "orderID")
    Order order;
}
