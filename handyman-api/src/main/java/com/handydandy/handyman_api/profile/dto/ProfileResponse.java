package com.handydandy.handyman_api.profile.dto;

import java.util.UUID;

public record ProfileResponse(
    UUID id,
    String name,
    String email,
    String role,
    UUID userId,
    LocationSummary location
) {
    public record LocationSummary(UUID id, String city, String state, String zip) {}
}
