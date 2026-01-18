package com.handydandy.handyman_api.booking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record BookingUpdateRequest(
    UUID locationId,

    LocalDateTime scheduledStart,

    LocalDateTime scheduledEnd,

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    String description,

    String status,

    @DecimalMin(value = "0.01", message = "Estimated cost must be positive")
    BigDecimal estimatedCost,

    @DecimalMin(value = "0.01", message = "Final cost must be positive")
    BigDecimal finalCost
) {}
