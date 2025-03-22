package org.group5.coolcafe.converter;

import org.group5.coolcafe.dto.manage_booking.ManageReserveTableDTO;
import org.group5.coolcafe.entity.ReserveTable;
import org.group5.coolcafe.service.ReserveTableService;
import org.springframework.stereotype.Component;

@Component
public class ReserveTableConverter {
    public ManageReserveTableDTO toManageReserveTableDTO(ReserveTable reserveTable) {
        ManageReserveTableDTO manageReserveTableDTO = new ManageReserveTableDTO();
        manageReserveTableDTO.setTable_code(reserveTable.getTable().getCode());
        manageReserveTableDTO.setUsername(reserveTable.getAccount().getUsername());
        manageReserveTableDTO.setTime(reserveTable.getTime());
        manageReserveTableDTO.setId(reserveTable.getId());
        return manageReserveTableDTO;
    }
}
