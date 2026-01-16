package com.handydandy.handyman_api.location;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    // Get all locations
    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    // Get location by ID
    @GetMapping("/{id}")
    public Optional<Location> getLocationById(@PathVariable UUID id) {
        return locationService.getLocationById(id);
    }

    // Create a new location
    @PostMapping
    public Location createLocation(@RequestBody Location location) {
        return locationService.createLocation(location);
    }

    // Update a location
    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable UUID id, @RequestBody Location location) {
        return locationService.updateLocation(id, location);
    }

    // Delete a location
    @DeleteMapping("/{id}")
    public void deleteLocation(@PathVariable UUID id) {
        locationService.deleteLocation(id);
    }
}