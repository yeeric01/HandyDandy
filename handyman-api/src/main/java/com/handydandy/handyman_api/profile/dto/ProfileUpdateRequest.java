package com.handydandy.handyman_api.profile.dto;

import jakarta.validation.constraints.Email;
import java.util.UUID;

public record ProfileUpdateRequest(
    String name,

    @Email(message = "Invalid email format")
    String email,

    UUID locationId
) {}
