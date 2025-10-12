package com.eventify.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Organiser login request")
public class LoginRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Organiser email address", example = "anas.yakubu@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Schema(description = "User password", example = "password123")
    private String password;
}
