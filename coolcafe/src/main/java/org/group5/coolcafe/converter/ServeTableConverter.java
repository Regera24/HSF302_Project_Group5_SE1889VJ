package org.group5.coolcafe.converter;

import org.group5.coolcafe.dto.booking_table.BookingServeTableDTO;
import org.group5.coolcafe.dto.order.OrderServeTableDTO;
import org.group5.coolcafe.entity.ServeTable;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ServeTableConverter {
    ModelMapper modelMapper = new ModelMapper();

    public BookingServeTableDTO toDTO (ServeTable serveTable) {
        return modelMapper.map(serveTable, BookingServeTableDTO.class);
    }

    public OrderServeTableDTO toServeTableDTO(ServeTable serveTable) {
        return modelMapper.map(serveTable, OrderServeTableDTO.class);
    }

}
