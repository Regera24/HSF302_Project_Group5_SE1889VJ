package org.group5.coolcafe.dto.manage_account;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManageAccountDTO {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and underscores")
    String username;

    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Tên chỉ được chứa chữ cái và khoảng trắng")
    String name;

    String gender,id,role;
    LocalDate dob;

    @Size(min = 8, max = 30)
    String password;

    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    @Pattern(regexp = "^\\+?[0-9\\s\\-]*$", message = "Phone number can only contain digits, spaces, or hyphens, and may start with +")
    String phoneNumber;

    @Email(message = "Email is not valid")
    String email;

    MultipartFile avatar;
}