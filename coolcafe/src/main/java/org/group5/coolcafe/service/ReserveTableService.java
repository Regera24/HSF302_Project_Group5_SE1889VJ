package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.booking_table.BookingReserveTableDTO;
import org.group5.coolcafe.dto.manage_booking.ManageReserveTableDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface ReserveTableService {
    public void add(BookingReserveTableDTO bookingReserveTableDTO);
    public Page<ManageReserveTableDTO> getAll(LocalDateTime startTime, LocalDateTime endTime, String tableCode, int page, int size, String sortBy, boolean descending);
    void update(Long id, LocalDateTime startTime, String tableCode);
    void delete(Long id);
}
