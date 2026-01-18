package com.handydandy.handyman_api.handymanservice;

import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.category.ServiceCategoryRepository;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceCreateRequest;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceResponse;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceUpdateRequest;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class HandymanServiceService {

    private final HandymanServiceRepository serviceRepository;
    private final ProfileRepository profileRepository;
    private final ServiceCategoryRepository categoryRepository;
    private final HandymanServiceMapper mapper;

    public HandymanServiceService(HandymanServiceRepository serviceRepository,
                                   ProfileRepository profileRepository,
                                   ServiceCategoryRepository categoryRepository,
                                   HandymanServiceMapper mapper) {
        this.serviceRepository = serviceRepository;
        this.profileRepository = profileRepository;
        this.categoryRepository = categoryRepository;
        this.mapper = mapper;
    }

    public HandymanServiceResponse createService(HandymanServiceCreateRequest request) {
        Profile handyman = profileRepository.findById(request.handymanId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));

        ServiceCategory category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("ServiceCategory", "id", request.categoryId()));

        HandymanService service = mapper.toEntity(request, handyman, category);
        return mapper.toResponse(serviceRepository.save(service));
    }

    @Transactional(readOnly = true)
    public Page<HandymanServiceResponse> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<HandymanServiceResponse> getServicesByHandyman(UUID handymanId, Pageable pageable) {
        return serviceRepository.findByHandymanId(handymanId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<HandymanServiceResponse> getServicesByCategory(UUID categoryId, Pageable pageable) {
        return serviceRepository.findByCategoryId(categoryId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public HandymanServiceResponse getServiceById(UUID id) {
        HandymanService service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HandymanService", "id", id));
        return mapper.toResponse(service);
    }

    public HandymanServiceResponse updateService(UUID id, HandymanServiceUpdateRequest request) {
        HandymanService service = serviceRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HandymanService", "id", id));
        mapper.updateEntity(service, request);
        return mapper.toResponse(serviceRepository.save(service));
    }

    public void deleteService(UUID id) {
        if (!serviceRepository.existsById(id)) {
            throw new ResourceNotFoundException("HandymanService", "id", id);
        }
        serviceRepository.deleteById(id);
    }
}
