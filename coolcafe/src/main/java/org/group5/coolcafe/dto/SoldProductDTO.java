package org.group5.coolcafe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SoldProductDTO {
    private Long id;
    private String name;
    private Long quantity;
}
