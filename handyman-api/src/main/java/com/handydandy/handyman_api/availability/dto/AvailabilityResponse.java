package com.handydandy.handyman_api.availability.dto;

import java.time.LocalTime;
import java.util.UUID;

public record AvailabilityResponse(
    UUID id,
    UUID handymanId,
    String dayOfWeek,
    LocalTime startTime,
    LocalTime endTime,
    boolean available
) {}
