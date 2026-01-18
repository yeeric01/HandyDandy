package com.handydandy.handyman_api.quote.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record QuoteCreateRequest(
    @NotNull(message = "Handyman ID is required")
    UUID handymanId,

    @NotNull(message = "Customer ID is required")
    UUID customerId,

    UUID serviceRequestId,

    @NotNull(message = "Category ID is required")
    UUID categoryId,

    @NotNull(message = "Labor cost is required")
    @DecimalMin(value = "0.01", message = "Labor cost must be positive")
    BigDecimal laborCost,

    @DecimalMin(value = "0.00", message = "Materials cost cannot be negative")
    BigDecimal materialsCost,

    @NotNull(message = "Total cost is required")
    @DecimalMin(value = "0.01", message = "Total cost must be positive")
    BigDecimal totalCost,

    @Min(value = 1, message = "Estimated hours must be at least 1")
    Integer estimatedHours,

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    String description,

    @Size(max = 500, message = "Terms cannot exceed 500 characters")
    String termsAndConditions,

    @NotNull(message = "Valid until date is required")
    @Future(message = "Valid until must be in the future")
    LocalDateTime validUntil
) {}
