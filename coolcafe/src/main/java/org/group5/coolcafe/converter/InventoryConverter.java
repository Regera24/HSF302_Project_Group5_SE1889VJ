package org.group5.coolcafe.converter;

import org.group5.coolcafe.dto.inventory.InventoryDTO;
import org.group5.coolcafe.entity.Inventory;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class InventoryConverter {
    ModelMapper modelMapper = new ModelMapper();

    public InventoryDTO toDTO(Inventory inventory) {
        return modelMapper.map(inventory, InventoryDTO.class);
    }
}
