package com.handydandy.handyman_api.availability;

import com.handydandy.handyman_api.availability.dto.AvailabilityCreateRequest;
import com.handydandy.handyman_api.availability.dto.AvailabilityResponse;
import com.handydandy.handyman_api.availability.dto.AvailabilityUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/availability")
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    @GetMapping("/handyman/{handymanId}")
    public ResponseEntity<List<AvailabilityResponse>> getAvailabilityByHandyman(
            @PathVariable UUID handymanId) {
        return ResponseEntity.ok(availabilityService.getAvailabilityByHandyman(handymanId));
    }

    @GetMapping("/handyman/{handymanId}/active")
    public ResponseEntity<List<AvailabilityResponse>> getActiveAvailabilityByHandyman(
            @PathVariable UUID handymanId) {
        return ResponseEntity.ok(availabilityService.getActiveAvailabilityByHandyman(handymanId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AvailabilityResponse> getAvailabilityById(@PathVariable UUID id) {
        return ResponseEntity.ok(availabilityService.getAvailabilityById(id));
    }

    @PostMapping
    public ResponseEntity<AvailabilityResponse> createAvailability(
            @Valid @RequestBody AvailabilityCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(availabilityService.createAvailability(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AvailabilityResponse> updateAvailability(
            @PathVariable UUID id,
            @Valid @RequestBody AvailabilityUpdateRequest request) {
        return ResponseEntity.ok(availabilityService.updateAvailability(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable UUID id) {
        availabilityService.deleteAvailability(id);
        return ResponseEntity.noContent().build();
    }
}
