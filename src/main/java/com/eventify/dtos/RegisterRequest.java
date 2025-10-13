package com.eventify.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Organiser registration request")
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    @Schema(description = "User full name", example = "Anas")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Schema(description = "User full name", example = "Yakubu")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "User email address", example = "anasyakubu@example.com")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Schema(description = "User password", example = "password123")
    private String password;

}
