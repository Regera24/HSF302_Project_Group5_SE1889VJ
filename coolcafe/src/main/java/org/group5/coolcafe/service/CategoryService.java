package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.CategoryDTO.CategoryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public interface CategoryService {
    public List<CategoryDTO> findAll();
    public List<CategoryDTO> findByName(String name, LocalDateTime dateFrom, LocalDateTime dateTo);
    public void createCategory(CategoryDTO categoryDTO);
    public void updateCategory(CategoryDTO categoryDTO);
    public void deleteCategory(long id);
}
