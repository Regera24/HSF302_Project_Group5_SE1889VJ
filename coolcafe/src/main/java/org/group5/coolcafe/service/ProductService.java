package org.group5.coolcafe.service;

import org.group5.coolcafe.dto.ProductDTO.ProductCreationRequest;
import org.group5.coolcafe.dto.ProductDTO.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ProductService {
    List<ProductDTO> findAll();
    ProductDTO findById(long id);
    Page<ProductDTO> findByName(int page, int size, String sortBy,
                                boolean descending, String name);
    Page<ProductDTO> findByNameAndCategory(int page, int size, String sortBy,
                                           boolean descending, String name,Long categoryId);
    void create(ProductCreationRequest productCreationRequest) throws IOException;
    void update(ProductCreationRequest productCreationRequest) throws IOException;
    void delete(long id);
}
