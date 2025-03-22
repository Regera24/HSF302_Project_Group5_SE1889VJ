package org.group5.coolcafe.controller.inventory;


import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.booking_table.BookingServeTableDTO;
import org.group5.coolcafe.dto.inventory.InventoryDTO;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("")
    public String inventory(
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "5") int size,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "descending", required = false, defaultValue = "false") boolean descending,
            Model model) {
        Page<InventoryDTO> list = inventoryService.getInventoryList(name, page, size, sortBy, descending);
        model.addAttribute("inventoryList", list);
        model.addAttribute("name", name);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("descending", descending);
        return "inventory/inventory";
    }

    @PostMapping("/update")
    public String updateInventory(
            @RequestParam("id") Long id,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("isImport") boolean isImport,
            RedirectAttributes redirectAttributes) {
        try {
            inventoryService.updateInventory(id, quantity, isImport);
        } catch (AppException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/inventory";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Inventory updated successfully!");
        return "redirect:/inventory";
    }
}
