package org.group5.coolcafe.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "AppStatistics")
public class Statistics extends AbstractEntity{
    @Column(name = "amount")
    Double amount;

    @Column(name = "description")
    String description;

    @Column(name = "type")
    String type;
}
