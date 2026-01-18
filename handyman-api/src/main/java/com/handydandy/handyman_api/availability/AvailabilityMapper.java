package com.handydandy.handyman_api.availability;

import com.handydandy.handyman_api.availability.dto.AvailabilityCreateRequest;
import com.handydandy.handyman_api.availability.dto.AvailabilityResponse;
import com.handydandy.handyman_api.availability.dto.AvailabilityUpdateRequest;
import com.handydandy.handyman_api.profile.Profile;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class AvailabilityMapper {

    public Availability toEntity(AvailabilityCreateRequest request, Profile handyman) {
        Availability availability = new Availability();
        availability.setHandyman(handyman);
        availability.setDayOfWeek(DayOfWeek.valueOf(request.dayOfWeek().toUpperCase()));
        availability.setStartTime(request.startTime());
        availability.setEndTime(request.endTime());
        return availability;
    }

    public AvailabilityResponse toResponse(Availability availability) {
        return new AvailabilityResponse(
            availability.getId(),
            availability.getHandyman() != null ? availability.getHandyman().getId() : null,
            availability.getDayOfWeek() != null ? availability.getDayOfWeek().name() : null,
            availability.getStartTime(),
            availability.getEndTime(),
            availability.isAvailable()
        );
    }

    public void updateEntity(Availability availability, AvailabilityUpdateRequest request) {
        if (request.startTime() != null) {
            availability.setStartTime(request.startTime());
        }
        if (request.endTime() != null) {
            availability.setEndTime(request.endTime());
        }
        if (request.available() != null) {
            availability.setAvailable(request.available());
        }
    }
}
