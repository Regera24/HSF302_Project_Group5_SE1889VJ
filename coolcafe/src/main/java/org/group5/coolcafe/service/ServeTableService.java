package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.booking_table.BookingServeTableDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public interface ServeTableService {
    public Page<BookingServeTableDTO> getBookingTable(LocalDateTime start, LocalDateTime end, int page, int size, String sortBy, boolean descending);
}