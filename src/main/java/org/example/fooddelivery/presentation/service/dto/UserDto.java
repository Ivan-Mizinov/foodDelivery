package org.example.fooddelivery.presentation.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.example.fooddelivery.domain.model.IUser;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDto implements IUser {
    private Long id;
    @NotBlank(message = "Name required")
    private String name;
    @NotBlank(message = "Email required")
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 3, message = "Password should be at least 3 characters")
    private String password;
    @NotBlank(message = "Phone number required")
    @Pattern(regexp = "^\\+?[78]\\s?\\(?\\d{3}\\)?[\\s\\-]?\\d{3}[\\s\\-]?\\d{2}[\\s\\-]?\\d{2}$", message = "Invalid phone number")
    private String phone;
    private String telegram;
    @NotBlank(message = "Address required")
    @Size(min = 4, message = "Address must be more than 4 characters")
    private String address;
}
