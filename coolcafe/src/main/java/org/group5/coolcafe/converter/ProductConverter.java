package org.group5.coolcafe.converter;

import lombok.RequiredArgsConstructor;
import org.group5.coolcafe.dto.CategoryDTO.CategoryDTO;
import org.group5.coolcafe.dto.ProductDTO.ProductDTO;
import org.group5.coolcafe.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class ProductConverter {
    private final ModelMapper modelMapper;

    public ProductDTO convertProductToProductDTO(Product product) {
        ProductDTO productDTO= new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setEstimatedQuantity(product.getEstimatedQuantity());
        productDTO.setSoldQuantity(product.getSoldQuantity());
        productDTO.setImage(product.getImage());
        if(product.getCategory()!=null){
            CategoryDTO categoryDTO = modelMapper.map(product.getCategory(), CategoryDTO.class);
            productDTO.setCategory(categoryDTO);
        }
        productDTO.setRate(product.getRate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        if (product.getCreatedAt() != null) {
            productDTO.setCreate_at(product.getCreatedAt().format(formatter));
        }
        if (product.getUpdatedAt() != null) {
            productDTO.setUpdate_at(product.getUpdatedAt().format(formatter));
        }
        return productDTO;
    }
    public Product convertProductDTOToProduct(ProductDTO productDTO) {
        return modelMapper.map(productDTO, Product.class);
    }
}
