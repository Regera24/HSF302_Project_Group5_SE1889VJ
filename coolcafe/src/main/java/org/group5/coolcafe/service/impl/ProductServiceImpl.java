package org.group5.coolcafe.service.impl;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.converter.CategoryConverter;
import org.group5.coolcafe.converter.ProductConverter;
import org.group5.coolcafe.dto.ProductDTO.ProductCreationRequest;
import org.group5.coolcafe.dto.ProductDTO.ProductDTO;
import org.group5.coolcafe.entity.Category;
import org.group5.coolcafe.entity.Product;
import org.group5.coolcafe.repository.CategoryRepository;
import org.group5.coolcafe.repository.ProductRepository;
import org.group5.coolcafe.service.ProductService;
import org.group5.coolcafe.utils.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryConverter categoryConverter;
    private final CloudinaryService cloudinaryService;
    private final CategoryRepository categoryRepository;


    @Override
    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(productConverter::convertProductToProductDTO).collect(Collectors.toList());
    }

    @Override
    public ProductDTO findById(long id) {
        Product a = productRepository.findById(id);
        return productConverter.convertProductToProductDTO(a);
    }


    @Override
    public Page<ProductDTO> findByName(int page, int size, String sortBy,
                                       boolean descending, String name) {
        if (name != null) {
            name = name.trim();
            name = name.toLowerCase();
        }
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findByName(name, pageable);
        return productPage.map(productConverter::convertProductToProductDTO);
    }

    @Override
    public Page<ProductDTO> findByNameAndCategory(int page, int size, String sortBy, boolean descending, String name, Long categoryId) {
        if (name != null) {
            name = name.trim();
            name = name.toLowerCase();
        }
        Sort sort = descending ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Product> productPage = productRepository.findByNameAndCategory(name,categoryId, pageable);
        return productPage.map(productConverter::convertProductToProductDTO);
    }

    @Override
    public void create(ProductCreationRequest productCreationRequest) throws IOException {
        String uploadedImageUrl ="default_image_url.png";
        if (productCreationRequest.getImage() != null && !productCreationRequest.getImage().isEmpty()) {
            uploadedImageUrl = cloudinaryService.uploadFile(productCreationRequest.getImage());
        }
        Product product = new Product();
        product.setName(productCreationRequest.getName());
        product.setDescription(productCreationRequest.getDescription());
        product.setImage(uploadedImageUrl);
        product.setPrice(productCreationRequest.getPrice());
        product.setRate(0);
        product.setEstimatedQuantity(productCreationRequest.getEstimatedQuantity());
        product.setSoldQuantity(0L);
        Category category = categoryRepository.findById(productCreationRequest.getCategoryId());
        product.setCategory(category);
        productRepository.save(product);

    }

    @Override
    public void update(ProductCreationRequest productCreationRequest) throws IOException {

        Product existingProduct = productRepository.findById(productCreationRequest.getId()).orElseThrow();
        String uploadedImageUrl = existingProduct.getImage();
        if (productCreationRequest.getImage() != null && !productCreationRequest.getImage().isEmpty()) {
            uploadedImageUrl = cloudinaryService.uploadFile(productCreationRequest.getImage());
        }
        existingProduct.setName(productCreationRequest.getName());
        existingProduct.setDescription(productCreationRequest.getDescription());
        existingProduct.setImage(uploadedImageUrl);
        existingProduct.setPrice(productCreationRequest.getPrice());
        existingProduct.setRate(0);
        existingProduct.setEstimatedQuantity(productCreationRequest.getEstimatedQuantity());
        existingProduct.setSoldQuantity(0L);
        Category category = categoryRepository.findById(productCreationRequest.getCategoryId());
        existingProduct.setCategory(category);
        productRepository.save(existingProduct);

    }

    @Override
    public void delete(long id) {
        Product product = productRepository.findById(id);
        if (product != null) {
            productRepository.delete(product);
        }
    }


}
