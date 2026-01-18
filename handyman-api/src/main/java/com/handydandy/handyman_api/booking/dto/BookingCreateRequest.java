package com.handydandy.handyman_api.booking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingCreateRequest(
    @NotNull(message = "Customer ID is required")
    UUID customerId,

    @NotNull(message = "Handyman ID is required")
    UUID handymanId,

    @NotNull(message = "Category ID is required")
    UUID categoryId,

    @NotNull(message = "Location ID is required")
    UUID locationId,

    UUID quoteId,

    @NotNull(message = "Scheduled start time is required")
    @Future(message = "Scheduled start must be in the future")
    LocalDateTime scheduledStart,

    @NotNull(message = "Scheduled end time is required")
    @Future(message = "Scheduled end must be in the future")
    LocalDateTime scheduledEnd,

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    String description,

    @DecimalMin(value = "0.01", message = "Estimated cost must be positive")
    BigDecimal estimatedCost
) {}
