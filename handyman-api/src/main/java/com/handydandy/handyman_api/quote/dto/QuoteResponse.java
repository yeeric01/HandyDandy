package com.handydandy.handyman_api.quote.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record QuoteResponse(
    UUID id,
    ProfileSummary handyman,
    ProfileSummary customer,
    UUID serviceRequestId,
    CategorySummary category,
    BigDecimal laborCost,
    BigDecimal materialsCost,
    BigDecimal totalCost,
    Integer estimatedHours,
    String description,
    String termsAndConditions,
    LocalDateTime validUntil,
    String status,
    LocalDateTime createdAt,
    LocalDateTime respondedAt
) {
    public record ProfileSummary(UUID id, String name, String email) {}
    public record CategorySummary(UUID id, String name) {}
}
