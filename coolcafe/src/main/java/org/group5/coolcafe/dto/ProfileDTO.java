package org.group5.coolcafe.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProfileDTO {

    @Size(min = 2, max = 50, message = "Tên phải từ 2 đến 50 ký tự")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Tên chỉ được chứa chữ cái và khoảng trắng")
    String name;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters long")
    String phoneNumber;

    LocalDate dob;

    @Email(message = "Email is not valid")
    String email;

    MultipartFile avatar;
}
