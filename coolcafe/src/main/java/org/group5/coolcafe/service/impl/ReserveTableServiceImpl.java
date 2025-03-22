package org.group5.coolcafe.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.ReserveTableConverter;
import org.group5.coolcafe.dto.booking_table.BookingReserveTableDTO;
import org.group5.coolcafe.dto.manage_booking.ManageReserveTableDTO;
import org.group5.coolcafe.entity.Account;
import org.group5.coolcafe.entity.ReserveTable;
import org.group5.coolcafe.entity.ServeTable;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.AccountRepository;
import org.group5.coolcafe.repository.ReserveTableRepository;
import org.group5.coolcafe.repository.ServeTableRepository;
import org.group5.coolcafe.service.AccountService;
import org.group5.coolcafe.service.ReserveTableService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReserveTableServiceImpl implements ReserveTableService {
    private final ReserveTableRepository reserveTableRepository;
    private final AccountRepository accountRepository;
    private final ServeTableRepository serveTableRepository;
    private final ReserveTableConverter reserveTableConverter;

    @Override
    @Transactional
    public void add(BookingReserveTableDTO dto) {
        Account account = accountRepository.findById(1L).orElse(null);
        ServeTable serveTable = serveTableRepository.findById(dto.getTable_id()).orElse(null);
        LocalDateTime startTime = dto.getTime();
        ReserveTable reserveTable = new ReserveTable();
        reserveTable.setTime(startTime);
        reserveTable.setAccount(account);
        reserveTable.setTable(serveTable);
        reserveTableRepository.save(reserveTable);
    }

    @Override
    @Transactional
    public Page<ManageReserveTableDTO> getAll(LocalDateTime startTime, LocalDateTime endTime, String tableCode, int page, int size, String sortBy, boolean descending){
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        tableCode = (tableCode != null && !tableCode.trim().isEmpty()) ? tableCode.trim() : null;
        return reserveTableRepository.findByTimeBetween(startTime, endTime, tableCode, pageable).map(reserveTableConverter::toManageReserveTableDTO);
    }

    @Override
    @Transactional
    public void update(Long id, LocalDateTime startTime, String tableCode) {
        ReserveTable reserveTable = reserveTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));

        ServeTable table = serveTableRepository.findByCode(tableCode)
                .orElseThrow(() -> new AppException(ErrorCode.TABLE_NOT_FOUND));

        reserveTable.setTime(startTime);
        reserveTable.setTable(table);
        reserveTableRepository.save(reserveTable);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ReserveTable reserveTable = reserveTableRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.NOT_FOUND));
        reserveTableRepository.delete(reserveTable);
    }
}
