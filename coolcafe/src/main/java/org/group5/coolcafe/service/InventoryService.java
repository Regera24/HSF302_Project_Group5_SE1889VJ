package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.inventory.InventoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface InventoryService {
    Page<InventoryDTO> getInventoryList(String name, int page, int size, String sortBy, boolean descending);
    void updateInventory(Long id, Integer quantity, boolean isImport);
}
