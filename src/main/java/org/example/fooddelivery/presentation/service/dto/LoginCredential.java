package org.example.fooddelivery.presentation.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class LoginCredential {
    @NotBlank(message = "Email required")
    @Email(message = "Email is not valid")
    private String email;
    @NotBlank(message = "Password required")
    @Size(min = 3, message = "Password should be at least 3 characters")
    private String password;
}
