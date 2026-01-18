package com.handydandy.handyman_api.quote.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record QuoteUpdateRequest(
    @DecimalMin(value = "0.01", message = "Labor cost must be positive")
    BigDecimal laborCost,

    @DecimalMin(value = "0.00", message = "Materials cost cannot be negative")
    BigDecimal materialsCost,

    @DecimalMin(value = "0.01", message = "Total cost must be positive")
    BigDecimal totalCost,

    @Min(value = 1, message = "Estimated hours must be at least 1")
    Integer estimatedHours,

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,

    @Size(max = 500, message = "Terms cannot exceed 500 characters")
    String termsAndConditions,

    LocalDateTime validUntil
) {}
