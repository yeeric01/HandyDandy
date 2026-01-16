package com.handydandy.handyman_api.servicerequest;

import org.springframework.stereotype.Service;

import com.handydandy.handyman_api.profile.Profile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository) {
        this.serviceRequestRepository = serviceRequestRepository;
    }

    // Create a new service request
    public ServiceRequest createRequest(ServiceRequest request) {
        request.setCreatedAt(LocalDateTime.now());
        request.setStatus(ServiceRequest.Status.PENDING);
        return serviceRequestRepository.save(request);
    }

    // Get all requests
    public List<ServiceRequest> getAllRequests() {
        return serviceRequestRepository.findAll();
    }

    // Get request by ID
    public Optional<ServiceRequest> getRequestById(UUID id) {
        return serviceRequestRepository.findById(id);
    }

    // Update a request
    public ServiceRequest updateRequest(UUID id, ServiceRequest updatedRequest) {
        return serviceRequestRepository.findById(id).map(request -> {
            request.setCustomer(updatedRequest.getCustomer());
            request.setHandyman(updatedRequest.getHandyman());
            request.setLocation(updatedRequest.getLocation());
            request.setDescription(updatedRequest.getDescription());
            request.setStatus(updatedRequest.getStatus());
            request.setUpdatedAt(LocalDateTime.now());
            return serviceRequestRepository.save(request);
        }).orElse(null);
    }

    // Delete a request
    public boolean deleteRequest(UUID id) {
        return serviceRequestRepository.findById(id).map(request -> {
            serviceRequestRepository.delete(request);
            return true;
        }).orElse(false);
    }

    // Assign a handyman to a request
    public ServiceRequest assignHandyman(UUID requestId, Profile handyman) {
        return serviceRequestRepository.findById(requestId).map(request -> {
            request.setHandyman(handyman);
            request.setStatus(ServiceRequest.Status.ACCEPTED);
            request.setUpdatedAt(LocalDateTime.now());
            return serviceRequestRepository.save(request);
        }).orElse(null);
    }
}