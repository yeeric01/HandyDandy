package com.handydandy.handyman_api.servicerequest;

import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestCreateRequest;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestResponse;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestUpdateRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ServiceRequestMapper {

    public ServiceRequest toEntity(ServiceRequestCreateRequest request, Profile customer, Profile handyman, Location location) {
        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setCustomer(customer);
        serviceRequest.setHandyman(handyman);
        serviceRequest.setLocation(location);
        serviceRequest.setDescription(request.description());
        serviceRequest.setStatus(ServiceRequest.Status.PENDING);
        serviceRequest.setCreatedAt(LocalDateTime.now());
        return serviceRequest;
    }

    public ServiceRequestResponse toResponse(ServiceRequest request) {
        ServiceRequestResponse.ProfileSummary customerSummary = null;
        if (request.getCustomer() != null) {
            Profile c = request.getCustomer();
            customerSummary = new ServiceRequestResponse.ProfileSummary(c.getId(), c.getName(), c.getEmail());
        }

        ServiceRequestResponse.ProfileSummary handymanSummary = null;
        if (request.getHandyman() != null) {
            Profile h = request.getHandyman();
            handymanSummary = new ServiceRequestResponse.ProfileSummary(h.getId(), h.getName(), h.getEmail());
        }

        ServiceRequestResponse.LocationSummary locationSummary = null;
        if (request.getLocation() != null) {
            Location loc = request.getLocation();
            locationSummary = new ServiceRequestResponse.LocationSummary(loc.getId(), loc.getCity(), loc.getState(), loc.getZip());
        }

        return new ServiceRequestResponse(
            request.getId(),
            customerSummary,
            handymanSummary,
            locationSummary,
            request.getDescription(),
            request.getStatus() != null ? request.getStatus().name() : null,
            request.getCreatedAt(),
            request.getUpdatedAt()
        );
    }

    public void updateEntity(ServiceRequest serviceRequest, ServiceRequestUpdateRequest request, Profile handyman, Location location) {
        if (request.description() != null) {
            serviceRequest.setDescription(request.description());
        }
        if (request.status() != null) {
            serviceRequest.setStatus(ServiceRequest.Status.valueOf(request.status().toUpperCase()));
        }
        if (handyman != null) {
            serviceRequest.setHandyman(handyman);
        }
        if (location != null) {
            serviceRequest.setLocation(location);
        }
        serviceRequest.setUpdatedAt(LocalDateTime.now());
    }
}
