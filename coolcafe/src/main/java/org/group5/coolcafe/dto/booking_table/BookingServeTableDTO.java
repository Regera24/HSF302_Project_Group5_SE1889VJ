package org.group5.coolcafe.dto.booking_table;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.group5.coolcafe.entity.ReserveTable;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class BookingServeTableDTO {
    Long id;

    String code;

    String description;

    String location;

    Boolean isActive;

    List<BookingReserveTableDTO> reserveTables;
}
