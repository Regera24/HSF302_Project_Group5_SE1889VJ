package org.group5.coolcafe.repository;

import org.group5.coolcafe.dto.booking_table.BookingReserveTableDTO;
import org.group5.coolcafe.entity.ReserveTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReserveTableRepository extends JpaRepository<ReserveTable, Long> {
    @Query("SELECT rt FROM ReserveTable rt " +
            "WHERE rt.table.id = :tableId " +
            "AND (" +
            "   (rt.time >= :startTime AND rt.time < :endTime) OR " +
            "   (rt.time < :startTime AND rt.time >= :adjustedStartTime) " +
            ")")
    List<ReserveTable> findOverlappingReservations(
            @Param("tableId") Long tableId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("adjustedStartTime") LocalDateTime adjustedStartTime
    );

    @Query("SELECT rt FROM ReserveTable rt " +
            "WHERE (" +
            "   (rt.time >= :startTime AND rt.time < :endTime) OR " +
            "   (rt.time < :startTime AND rt.time >= :adjustedStartTime) " +
            ")")
    List<ReserveTable> findAllOverlappingReservations(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("adjustedStartTime") LocalDateTime adjustedStartTime
    );

    @Query("SELECT rt FROM ReserveTable rt " +
            "WHERE (:startTime IS NULL OR rt.time >= :startTime) " +
            "AND (:endTime IS NULL OR rt.time <= :endTime)" +
            "AND (:tableCode IS NULL OR LOWER(rt.table.code) LIKE LOWER(CONCAT('%', :tableCode, '%')))")
    Page<ReserveTable> findByTimeBetween(@Param("startTime") LocalDateTime startTime,
                                         @Param("endTime") LocalDateTime endTime,
                                         @Param("tableCode") String tableCode,
                                         Pageable pageable);
}
