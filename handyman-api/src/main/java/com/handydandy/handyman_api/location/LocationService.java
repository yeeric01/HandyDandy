package com.handydandy.handyman_api.location;

import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.location.dto.LocationCreateRequest;
import com.handydandy.handyman_api.location.dto.LocationResponse;
import com.handydandy.handyman_api.location.dto.LocationUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper mapper;

    public LocationService(LocationRepository locationRepository, LocationMapper mapper) {
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }

    public LocationResponse createLocation(LocationCreateRequest request) {
        Location location = mapper.toEntity(request);
        return mapper.toResponse(locationRepository.save(location));
    }

    @Transactional(readOnly = true)
    public Page<LocationResponse> getAllLocations(Pageable pageable) {
        return locationRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public LocationResponse getLocationById(UUID id) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
        return mapper.toResponse(location);
    }

    public LocationResponse updateLocation(UUID id, LocationUpdateRequest request) {
        Location location = locationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
        mapper.updateEntity(location, request);
        return mapper.toResponse(locationRepository.save(location));
    }

    public void deleteLocation(UUID id) {
        if (!locationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Location", "id", id);
        }
        locationRepository.deleteById(id);
    }

    // Internal method to get entity (for other services)
    @Transactional(readOnly = true)
    public Location getEntityById(UUID id) {
        return locationRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Location", "id", id));
    }
}
