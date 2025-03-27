package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.manage_booking.ManageReserveTableDTO;
import org.group5.coolcafe.dto.order.OrderServeTableDTO;
import org.group5.coolcafe.entity.ReserveTable;
import org.group5.coolcafe.entity.ServeTable;
import org.group5.coolcafe.service.ReserveTableService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReserveTableConverter {
    private final ModelMapper modelMapper;
    public ManageReserveTableDTO toManageReserveTableDTO(ReserveTable reserveTable) {
        ManageReserveTableDTO manageReserveTableDTO = new ManageReserveTableDTO();
        manageReserveTableDTO.setTable_code(reserveTable.getTable().getCode());
        manageReserveTableDTO.setUsername(reserveTable.getAccount().getUsername());
        manageReserveTableDTO.setName(reserveTable.getAccount().getName());
        manageReserveTableDTO.setPhoneNumber(reserveTable.getAccount().getPhoneNumber());
        manageReserveTableDTO.setTime(reserveTable.getTime());
        manageReserveTableDTO.setId(reserveTable.getId());
        return manageReserveTableDTO;
    }

    public OrderServeTableDTO toServeTableDTO(ServeTable serveTable) {
        return modelMapper.map(serveTable, OrderServeTableDTO.class);
    }

}
