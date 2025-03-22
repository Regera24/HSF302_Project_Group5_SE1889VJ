package org.group5.coolcafe.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.InventoryConverter;
import org.group5.coolcafe.dto.inventory.InventoryDTO;
import org.group5.coolcafe.entity.Inventory;
import org.group5.coolcafe.exception.AppException;
import org.group5.coolcafe.exception.ErrorCode;
import org.group5.coolcafe.repository.InventoryRepository;
import org.group5.coolcafe.service.InventoryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    private final InventoryRepository inventoryRepository;
    private final InventoryConverter inventoryConverter;

    @Override
    public Page<InventoryDTO> getInventoryList(String name, int page, int size, String sortBy, boolean descending) {
        name = (name != null && !name.trim().isEmpty()) ? name.trim() : null;
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return inventoryRepository.findByName(name, pageable).map(inventoryConverter::toDTO);
    }

    @Override
    @Transactional
    public void updateInventory(Long id, Integer quantity, boolean isImport) {
        Inventory inventory = inventoryRepository.findById(id).orElse(null);
        if(inventory == null) {
            throw new AppException(ErrorCode.NOT_FOUND);
        }
        if (isImport) {
            inventory.setQuantity(inventory.getQuantity() + quantity);
        } else {
            if (inventory.getQuantity() < quantity) {
                throw new AppException(ErrorCode.NOT_ENOUGH_QUANTITY);
            }
            inventory.setQuantity(inventory.getQuantity() - quantity);
        }
        inventoryRepository.save(inventory);
    }
}
