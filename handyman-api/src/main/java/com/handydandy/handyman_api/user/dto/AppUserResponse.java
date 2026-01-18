package com.handydandy.handyman_api.user.dto;

import java.util.UUID;

public record AppUserResponse(
    UUID id,
    String email,
    String role
) {}
