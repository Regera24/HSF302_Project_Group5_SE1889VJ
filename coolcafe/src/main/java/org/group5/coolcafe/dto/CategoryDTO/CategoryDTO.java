package org.group5.coolcafe.dto.CategoryDTO;


import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryDTO {
    Long id;
    String create_at;
    String update_at;
    String name;
    String description;
}
