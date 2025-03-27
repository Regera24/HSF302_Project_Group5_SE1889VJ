package org.group5.coolcafe.controller.booking_table;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.booking_table.BookingReserveTableDTO;
import org.group5.coolcafe.dto.booking_table.BookingServeTableDTO;
import org.group5.coolcafe.service.ReserveTableService;
import org.group5.coolcafe.service.ServeTableService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/booking-table")
public class BookingTableController {
    private final ServeTableService serveTableService;
    private final ReserveTableService reserveTableService;

    @GetMapping("")
    public String bookingTable() {
        return "table-booking/table-booking";
    }

    @GetMapping("list-table")
    public String getBookingTable(
            @RequestParam(name = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "descending", required = false, defaultValue = "false") boolean descending,
            Model model) {
        if (startTime == null) {
            model.addAttribute("errorMessage", "Vui lòng chọn Thời gian Bắt đầu để tìm bàn khả dụng.");
            return "table-booking/table-booking";
        }
        if (startTime.isBefore(LocalDateTime.now())) {
            model.addAttribute("errorMessage", "Vui lòng chọn Thời gian khả dụng");
            return "table-booking/table-booking";
        }
        LocalDateTime endTime = startTime.plusHours(3);
        Page<BookingServeTableDTO> serveTables = serveTableService.getBookingTable(startTime, endTime, page, size, sortBy, descending);
        model.addAttribute("startTime", startTime);
        model.addAttribute("serveTables", serveTables);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("descending", descending);
        return "table-booking/table-booking";
    }

    @PostMapping("/booking")
    public String booking(
            @RequestParam(name = "startTime") LocalDateTime startTime,
            @RequestParam("tableId") int tableId,
            Model model) {
        try {
            BookingReserveTableDTO bookingReserveTableDTO = new BookingReserveTableDTO();
            bookingReserveTableDTO.setTime(startTime);
            bookingReserveTableDTO.setTable_id((long) tableId);
            reserveTableService.add(bookingReserveTableDTO);
            model.addAttribute("successMessage", "Đặt bàn thành công!");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi khi đặt bàn!");
        }
        return "table-booking/table-booking";
    }
}