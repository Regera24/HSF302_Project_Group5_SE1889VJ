package org.group5.coolcafe.dto.inventory;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class InventoryDTO {
    Long id;
    LocalDateTime createTime;
    String name;
    String supplier;
    Integer quantity;
    String description;
    LocalDate expiredAt;
}
