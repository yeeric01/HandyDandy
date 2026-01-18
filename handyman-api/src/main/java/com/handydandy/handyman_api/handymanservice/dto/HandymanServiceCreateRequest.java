package com.handydandy.handyman_api.handymanservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record HandymanServiceCreateRequest(
    @NotNull(message = "Handyman ID is required")
    UUID handymanId,

    @NotNull(message = "Category ID is required")
    UUID categoryId,

    @NotNull(message = "Hourly rate is required")
    @DecimalMin(value = "0.01", message = "Hourly rate must be positive")
    BigDecimal hourlyRate,

    @Min(value = 0, message = "Years of experience cannot be negative")
    Integer yearsExperience,

    String certifications
) {}
