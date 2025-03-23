package org.group5.coolcafe.dto.ProductDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;


@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreationRequest {
    Long id;
    String create_at;
    String update_at;
    @NotBlank(message = "Product name cannot be empty")
    @Size(max = 100, message = "Product name cannot exceed 100 characters")
    String name;
    String description;
    @NotNull(message = "Price must be number")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    Double price;
    Long soldQuantity;
    MultipartFile image;
    @NotNull(message = "Estimated quantity must be number")
    @Min(value = 0, message = "Estimated quantity must be greater than or equal to 0")
    Long estimatedQuantity;
    Integer rate;
    long categoryId;
 }
