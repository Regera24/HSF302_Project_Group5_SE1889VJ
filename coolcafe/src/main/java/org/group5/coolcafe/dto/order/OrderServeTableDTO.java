package org.group5.coolcafe.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderServeTableDTO {
    Long serveTableId;
    String code;
    String location;
}
