package org.group5.coolcafe.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.CategoryConverter;
import org.group5.coolcafe.dto.CategoryDTO.CategoryDTO;
import org.group5.coolcafe.entity.Category;
import org.group5.coolcafe.repository.CategoryRepository;
import org.group5.coolcafe.service.CategoryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream().map(categoryConverter::convertCategoryToCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> findByName(String name, LocalDateTime dateFrom, LocalDateTime dateTo) {
        if (name != null) {
            name = name.trim();
            name = name.toLowerCase();
        }
        List<Category> a=new ArrayList<>();
        a=categoryRepository.findByName(name,dateFrom,dateTo);
        return a.stream().map(categoryConverter::convertCategoryToCategoryDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void createCategory(CategoryDTO categoryDTO) {
        categoryDTO.setName(capitalizeFirstLetters(categoryDTO.getName()));
        Category a=categoryConverter.convertCategoryDTOToCategory(categoryDTO);
        categoryRepository.save(a);
    }

    @Override
    @Transactional
    public void updateCategory(CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category with ID " + categoryDTO.getId() + " not found"));

        existingCategory.setName(capitalizeFirstLetters(categoryDTO.getName()));
        existingCategory.setDescription(categoryDTO.getDescription());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(long id) {
        Category existingCategory = categoryRepository.findById(id);
        if (existingCategory != null) {
            categoryRepository.delete(existingCategory);
        }
    }

    public String capitalizeFirstLetters(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        String[] words = input.split("\\s+");
        StringBuilder capitalizedString = new StringBuilder();
        for (String word : words) {
            if (word.length() > 0) {
                capitalizedString.append(word.substring(0, 1).toUpperCase())
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return capitalizedString.toString().trim();
    }
}
