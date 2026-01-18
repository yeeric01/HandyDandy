package com.handydandy.handyman_api.profile;

import com.handydandy.handyman_api.exception.DuplicateResourceException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.location.LocationRepository;
import com.handydandy.handyman_api.profile.dto.ProfileCreateRequest;
import com.handydandy.handyman_api.profile.dto.ProfileResponse;
import com.handydandy.handyman_api.profile.dto.ProfileUpdateRequest;
import com.handydandy.handyman_api.user.AppUser;
import com.handydandy.handyman_api.user.AppUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final AppUserRepository userRepository;
    private final LocationRepository locationRepository;
    private final ProfileMapper mapper;

    public ProfileService(ProfileRepository profileRepository,
                          AppUserRepository userRepository,
                          LocationRepository locationRepository,
                          ProfileMapper mapper) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.mapper = mapper;
    }

    public ProfileResponse createProfile(ProfileCreateRequest request) {
        if (profileRepository.findByEmail(request.email()).isPresent()) {
            throw new DuplicateResourceException("Profile with email '" + request.email() + "' already exists");
        }

        AppUser user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId()));

        Location location = null;
        if (request.locationId() != null) {
            location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", request.locationId()));
        }

        Profile profile = mapper.toEntity(request, user, location);
        return mapper.toResponse(profileRepository.save(profile));
    }

    @Transactional(readOnly = true)
    public Page<ProfileResponse> getAllProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProfileResponse getProfileById(UUID id) {
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", id));
        return mapper.toResponse(profile);
    }

    public ProfileResponse updateProfile(UUID id, ProfileUpdateRequest request) {
        Profile profile = profileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", id));

        if (request.email() != null && !request.email().equals(profile.getEmail())) {
            if (profileRepository.findByEmail(request.email()).isPresent()) {
                throw new DuplicateResourceException("Profile with email '" + request.email() + "' already exists");
            }
        }

        Location location = null;
        if (request.locationId() != null) {
            location = locationRepository.findById(request.locationId())
                .orElseThrow(() -> new ResourceNotFoundException("Location", "id", request.locationId()));
        }

        mapper.updateEntity(profile, request, location);
        return mapper.toResponse(profileRepository.save(profile));
    }

    public void deleteProfile(UUID id) {
        if (!profileRepository.existsById(id)) {
            throw new ResourceNotFoundException("Profile", "id", id);
        }
        profileRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<ProfileResponse> getAllHandymen(Pageable pageable) {
        return profileRepository.findByRole("HANDYMAN", pageable).map(mapper::toResponse);
    }

    // Internal method to get entity (for other services)
    @Transactional(readOnly = true)
    public Profile getEntityById(UUID id) {
        return profileRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", id));
    }
}
