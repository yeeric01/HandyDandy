package com.handydandy.handyman_api.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingResponse(
    UUID id,
    ProfileSummary customer,
    ProfileSummary handyman,
    CategorySummary category,
    LocationSummary location,
    UUID quoteId,
    LocalDateTime scheduledStart,
    LocalDateTime scheduledEnd,
    String description,
    String status,
    BigDecimal estimatedCost,
    BigDecimal finalCost,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    LocalDateTime completedAt
) {
    public record ProfileSummary(UUID id, String name, String email) {}
    public record CategorySummary(UUID id, String name) {}
    public record LocationSummary(UUID id, String city, String state, String zip) {}
}
