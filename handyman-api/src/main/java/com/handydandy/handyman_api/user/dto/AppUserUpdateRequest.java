package com.handydandy.handyman_api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record AppUserUpdateRequest(
    @Email(message = "Invalid email format")
    String email,

    @Size(min = 8, message = "Password must be at least 8 characters")
    String password
) {}
