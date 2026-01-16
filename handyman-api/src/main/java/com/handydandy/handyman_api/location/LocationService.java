package com.handydandy.handyman_api.location;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Create a new location
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    // Get all locations
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    // Get location by ID
    public Optional<Location> getLocationById(UUID id) {
        return locationRepository.findById(id);
    }

    // Update location
    public Location updateLocation(UUID id, Location updatedLocation) {
        return locationRepository.findById(id).map(location -> {
            location.setCity(updatedLocation.getCity());
            location.setState(updatedLocation.getState());
            location.setZip(updatedLocation.getZip());
            return locationRepository.save(location);
        }).orElse(null);
    }

    // Delete location
    public boolean deleteLocation(UUID id) {
        return locationRepository.findById(id).map(location -> {
            locationRepository.delete(location);
            return true;
        }).orElse(false);
    }
}