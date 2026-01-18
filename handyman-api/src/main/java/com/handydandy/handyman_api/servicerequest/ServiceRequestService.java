package com.handydandy.handyman_api.servicerequest;

import com.handydandy.handyman_api.exception.BadRequestException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.location.LocationRepository;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestCreateRequest;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestResponse;
import com.handydandy.handyman_api.servicerequest.dto.ServiceRequestUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class ServiceRequestService {

    private final ServiceRequestRepository serviceRequestRepository;
    private final ProfileRepository profileRepository;
    private final LocationRepository locationRepository;
    private final ServiceRequestMapper mapper;

    public ServiceRequestService(ServiceRequestRepository serviceRequestRepository,
                                  ProfileRepository profileRepository,
                                  LocationRepository locationRepository,
                                  ServiceRequestMapper mapper) {
        this.serviceRequestRepository = serviceRequestRepository;
        this.profileRepository = profileRepository;
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }

    public ServiceRequestResponse createRequest(ServiceRequestCreateRequest request) {
        Profile customer = profileRepository.findById(request.customerId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.customerId()));

        Profile handyman = null;
        if (request.handymanId() != null) {
            handyman = profileRepository.findById(request.handymanId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));
        }

        Location location = locationRepository.findById(request.locationId())
            .orElseThrow(() -> new ResourceNotFoundException("Location", "id", request.locationId()));

        ServiceRequest serviceRequest = mapper.toEntity(request, customer, handyman, location);
        return mapper.toResponse(serviceRequestRepository.save(serviceRequest));
    }

    @Transactional(readOnly = true)
    public Page<ServiceRequestResponse> getAllRequests(Pageable pageable) {
        return serviceRequestRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ServiceRequestResponse getRequestById(UUID id) {
        ServiceRequest request = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest", "id", id));
        return mapper.toResponse(request);
    }

    @Transactional(readOnly = true)
    public Page<ServiceRequestResponse> getRequestsByCustomer(UUID customerId, Pageable pageable) {
        return serviceRequestRepository.findByCustomerId(customerId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ServiceRequestResponse> getRequestsByHandyman(UUID handymanId, Pageable pageable) {
        return serviceRequestRepository.findByHandymanId(handymanId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ServiceRequestResponse> getRequestsByStatus(String status, Pageable pageable) {
        ServiceRequest.Status statusEnum = ServiceRequest.Status.valueOf(status.toUpperCase());
        return serviceRequestRepository.findByStatus(statusEnum, pageable).map(mapper::toResponse);
    }

    public ServiceRequestResponse updateRequest(UUID id, ServiceRequestUpdateRequest request) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest", "id", id));

        Profile handyman = null;
        if (request.handymanId() != null) {
            handyman = profileRepository.findById(request.handymanId())
                .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));
        }

        Location location = null;
        if (request.locationId() != null) {
            location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", request.locationId()));
        }

        mapper.updateEntity(serviceRequest, request, handyman, location);
        return mapper.toResponse(serviceRequestRepository.save(serviceRequest));
    }

    public ServiceRequestResponse assignHandyman(UUID requestId, UUID handymanId) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(requestId)
            .orElseThrow(() -> new ResourceNotFoundException("ServiceRequest", "id", requestId));

        if (serviceRequest.getStatus() != ServiceRequest.Status.PENDING) {
            throw new BadRequestException("Can only assign handyman to pending requests");
        }

        Profile handyman = profileRepository.findById(handymanId)
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", handymanId));

        serviceRequest.setHandyman(handyman);
        serviceRequest.setStatus(ServiceRequest.Status.ACCEPTED);
        serviceRequest.setUpdatedAt(LocalDateTime.now());

        return mapper.toResponse(serviceRequestRepository.save(serviceRequest));
    }

    public void deleteRequest(UUID id) {
        if (!serviceRequestRepository.existsById(id)) {
            throw new ResourceNotFoundException("ServiceRequest", "id", id);
        }
        serviceRequestRepository.deleteById(id);
    }
}
