package org.group5.coolcafe.dto.ProductDTO;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.group5.coolcafe.dto.CategoryDTO.CategoryDTO;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDTO {
    Long id;
    String create_at;
    String update_at;
    String name;
    String description;
    Double price;
    Long soldQuantity;
    String image;
    Long estimatedQuantity;
    Integer rate;
    CategoryDTO category;
}
