package com.handydandy.handyman_api.availability.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.UUID;

public record AvailabilityCreateRequest(
    @NotNull(message = "Handyman ID is required")
    UUID handymanId,

    @NotBlank(message = "Day of week is required")
    String dayOfWeek,

    @NotNull(message = "Start time is required")
    LocalTime startTime,

    @NotNull(message = "End time is required")
    LocalTime endTime
) {}
