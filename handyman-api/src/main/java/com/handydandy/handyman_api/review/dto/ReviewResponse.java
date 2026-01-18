package com.handydandy.handyman_api.review.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReviewResponse(
    UUID id,
    ProfileSummary reviewer,
    ProfileSummary handyman,
    UUID bookingId,
    Integer rating,
    String comment,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public record ProfileSummary(UUID id, String name, String email) {}
}
