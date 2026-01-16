package com.handydandy.handyman_api.servicerequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.handydandy.handyman_api.profile.Profile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/service-requests")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    // Get all requests
    @GetMapping
    public List<ServiceRequest> getAllRequests() {
        return serviceRequestService.getAllRequests();
    }

    // Get request by ID
    @GetMapping("/{id}")
    public Optional<ServiceRequest> getRequestById(@PathVariable UUID id) {
        return serviceRequestService.getRequestById(id);
    }

    // Create a new request
    @PostMapping
    public ServiceRequest createRequest(@RequestBody ServiceRequest request) {
        return serviceRequestService.createRequest(request);
    }

    // Update a request
    @PutMapping("/{id}")
    public ServiceRequest updateRequest(@PathVariable UUID id, @RequestBody ServiceRequest request) {
        return serviceRequestService.updateRequest(id, request);
    }

    // Delete a request
    @DeleteMapping("/{id}")
    public void deleteRequest(@PathVariable UUID id) {
        serviceRequestService.deleteRequest(id);
    }

    // Assign handyman to a request
    @PostMapping("/{id}/assign")
    public ServiceRequest assignHandyman(@PathVariable UUID id, @RequestBody Profile handyman) {
        return serviceRequestService.assignHandyman(id, handyman);
    }
}