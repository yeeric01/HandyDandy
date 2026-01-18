package com.handydandy.handyman_api.handymanservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record HandymanServiceResponse(
    UUID id,
    ProfileSummary handyman,
    CategorySummary category,
    BigDecimal hourlyRate,
    Integer yearsExperience,
    String certifications,
    boolean active
) {
    public record ProfileSummary(UUID id, String name, String email) {}
    public record CategorySummary(UUID id, String name) {}
}
