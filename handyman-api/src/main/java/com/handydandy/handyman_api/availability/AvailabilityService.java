package com.handydandy.handyman_api.availability;

import com.handydandy.handyman_api.availability.dto.AvailabilityCreateRequest;
import com.handydandy.handyman_api.availability.dto.AvailabilityResponse;
import com.handydandy.handyman_api.availability.dto.AvailabilityUpdateRequest;
import com.handydandy.handyman_api.exception.DuplicateResourceException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final ProfileRepository profileRepository;
    private final AvailabilityMapper mapper;

    public AvailabilityService(AvailabilityRepository availabilityRepository,
                                ProfileRepository profileRepository,
                                AvailabilityMapper mapper) {
        this.availabilityRepository = availabilityRepository;
        this.profileRepository = profileRepository;
        this.mapper = mapper;
    }

    public AvailabilityResponse createAvailability(AvailabilityCreateRequest request) {
        Profile handyman = profileRepository.findById(request.handymanId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));

        DayOfWeek day = DayOfWeek.valueOf(request.dayOfWeek().toUpperCase());
        if (availabilityRepository.findByHandymanIdAndDayOfWeek(request.handymanId(), day).isPresent()) {
            throw new DuplicateResourceException("Availability for this day already exists");
        }

        Availability availability = mapper.toEntity(request, handyman);
        return mapper.toResponse(availabilityRepository.save(availability));
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponse> getAvailabilityByHandyman(UUID handymanId) {
        return availabilityRepository.findByHandymanId(handymanId).stream()
            .map(mapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public List<AvailabilityResponse> getActiveAvailabilityByHandyman(UUID handymanId) {
        return availabilityRepository.findByHandymanIdAndAvailableTrue(handymanId).stream()
            .map(mapper::toResponse)
            .toList();
    }

    @Transactional(readOnly = true)
    public AvailabilityResponse getAvailabilityById(UUID id) {
        Availability availability = availabilityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", id));
        return mapper.toResponse(availability);
    }

    public AvailabilityResponse updateAvailability(UUID id, AvailabilityUpdateRequest request) {
        Availability availability = availabilityRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", id));
        mapper.updateEntity(availability, request);
        return mapper.toResponse(availabilityRepository.save(availability));
    }

    public void deleteAvailability(UUID id) {
        if (!availabilityRepository.existsById(id)) {
            throw new ResourceNotFoundException("Availability", "id", id);
        }
        availabilityRepository.deleteById(id);
    }
}
