package com.handydandy.handyman_api.location;

import com.handydandy.handyman_api.location.dto.LocationCreateRequest;
import com.handydandy.handyman_api.location.dto.LocationResponse;
import com.handydandy.handyman_api.location.dto.LocationUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {

    public Location toEntity(LocationCreateRequest request) {
        Location location = new Location();
        location.setCity(request.city());
        location.setState(request.state());
        location.setZip(request.zip());
        return location;
    }

    public LocationResponse toResponse(Location location) {
        return new LocationResponse(
            location.getId(),
            location.getCity(),
            location.getState(),
            location.getZip()
        );
    }

    public void updateEntity(Location location, LocationUpdateRequest request) {
        if (request.city() != null) {
            location.setCity(request.city());
        }
        if (request.state() != null) {
            location.setState(request.state());
        }
        if (request.zip() != null) {
            location.setZip(request.zip());
        }
    }
}
