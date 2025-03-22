package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.ServeTableConverter;
import org.group5.coolcafe.dto.booking_table.BookingReserveTableDTO;
import org.group5.coolcafe.dto.booking_table.BookingServeTableDTO;
import org.group5.coolcafe.entity.ReserveTable;
import org.group5.coolcafe.entity.ServeTable;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.ReserveTableRepository;
import org.group5.coolcafe.repository.ServeTableRepository;
import org.group5.coolcafe.service.ServeTableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServeTableServiceImpl implements ServeTableService {

    private final ServeTableRepository serveTableRepository;
    private final ServeTableConverter serveTableConverter;
    private final ReserveTableRepository reserveTableRepository;

    @Override
    public Page<BookingServeTableDTO> getBookingTable(LocalDateTime start, LocalDateTime end, int page, int size, String sortBy, boolean descending){
        if(start == null || end == null){
            throw new AppException(ErrorCode.TIME_CANT_NOT_BE_NULL);
        }
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        LocalDateTime adjustedStartTime = start.minusHours(3);
        List<ReserveTable> overlappingReservations = reserveTableRepository.findAllOverlappingReservations(start, end, adjustedStartTime);
        List<Long> reservedTableIds = overlappingReservations.stream()
                .map(reserveTable -> reserveTable.getTable().getId())
                .distinct()
                .toList();
        Page<ServeTable> availableServeTablesPage = serveTableRepository.findByIdNotIn(reservedTableIds, pageable);
        return availableServeTablesPage.map(serveTableConverter::toDTO);
    }
}
