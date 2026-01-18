package com.handydandy.handyman_api.review.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ReviewCreateRequest(
    @NotNull(message = "Reviewer ID is required")
    UUID reviewerId,

    @NotNull(message = "Handyman ID is required")
    UUID handymanId,

    @NotNull(message = "Booking ID is required")
    UUID bookingId,

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating cannot exceed 5")
    Integer rating,

    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    String comment
) {}
