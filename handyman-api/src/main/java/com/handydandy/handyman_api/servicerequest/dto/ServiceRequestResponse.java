package com.handydandy.handyman_api.servicerequest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ServiceRequestResponse(
    UUID id,
    ProfileSummary customer,
    ProfileSummary handyman,
    LocationSummary location,
    String description,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record ProfileSummary(UUID id, String name, String email) {}
    public record LocationSummary(UUID id, String city, String state, String zip) {}
}
