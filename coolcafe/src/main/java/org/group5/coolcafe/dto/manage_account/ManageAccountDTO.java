package org.group5.coolcafe.dto.manage_account;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManageAccountDTO {

    String username;

    String name, gender,id,role,dob;

    @NotNull(message = "Password cannot be null")
    String password;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 characters long")
    String phoneNumber;

    @Email(message = "Email is not valid")
    String email;

    MultipartFile avatar;
}