package com.handydandy.handyman_api.availability.dto;

import java.time.LocalTime;

public record AvailabilityUpdateRequest(
    LocalTime startTime,
    LocalTime endTime,
    Boolean available
) {}
