package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.CategoryDTO.CategoryDTO;
import org.group5.coolcafe.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class CategoryConverter {
    private final ModelMapper modelMapper;

    public CategoryDTO convertCategoryToCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (category.getCreatedAt() != null) {
            categoryDTO.setCreate_at(category.getCreatedAt().format(formatter));
        }
        if (category.getUpdatedAt() != null) {
            categoryDTO.setUpdate_at(category.getUpdatedAt().format(formatter));
        }
        return categoryDTO;
    }
    public Category convertCategoryDTOToCategory(CategoryDTO categoryDTO) {
        return modelMapper.map(categoryDTO, Category.class);
    }
}
