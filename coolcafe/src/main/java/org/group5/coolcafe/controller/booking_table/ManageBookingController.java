package org.group5.coolcafe.controller.booking_table;


import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.manage_booking.ManageReserveTableDTO;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.service.ReserveTableService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/manage-booking")
public class ManageBookingController {
    private final ReserveTableService reserveTableService;

    @GetMapping("")
    public String getBookingTable(
            @RequestParam(name = "startTime", required = false) LocalDateTime startTime,
            @RequestParam(name = "endTime", required = false) LocalDateTime endTime,
            @RequestParam(name = "table_code", required = false) String tableCode,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "descending", required = false, defaultValue = "false") boolean descending,
            Model model) {
        Page<ManageReserveTableDTO> reserveTables = reserveTableService.getAll(startTime, endTime, tableCode, page, size, sortBy, descending);
        model.addAttribute("startTime", startTime);
        model.addAttribute("endTime", endTime);
        model.addAttribute("table_code", tableCode);
        model.addAttribute("reserveTables", reserveTables);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("descending", descending);
        return "manage-booking/manage-booking";
    }

    @PostMapping("/update")
    public String updateBooking(@RequestParam("id") Long id,
                                @RequestParam("startTime") LocalDateTime startTime,
                                @RequestParam("tableCode") String tableCode,
                                RedirectAttributes redirectAttributes) {
        try {
            reserveTableService.update(id, startTime, tableCode);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory updated successfully!");
            return "redirect:/manage-booking";
        } catch (AppException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/manage-booking";
        }
    }

    @PostMapping("/delete")
    public String deleteBooking(@RequestParam("id") Long id, RedirectAttributes redirectAttributes) {
        System.out.println("id"+ id);
        try {
            reserveTableService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Booking deleted successfully!");
            return "redirect:/manage-booking";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/manage-booking";
        }
    }
}