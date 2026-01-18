package com.handydandy.handyman_api.profile;

import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.profile.dto.ProfileCreateRequest;
import com.handydandy.handyman_api.profile.dto.ProfileResponse;
import com.handydandy.handyman_api.profile.dto.ProfileUpdateRequest;
import com.handydandy.handyman_api.user.AppUser;
import org.springframework.stereotype.Component;

@Component
public class ProfileMapper {

    public Profile toEntity(ProfileCreateRequest request, AppUser user, Location location) {
        Profile profile = new Profile();
        profile.setName(request.name());
        profile.setEmail(request.email());
        profile.setUser(user);
        profile.setRole(user.getRole() != null ? user.getRole().name() : null);
        profile.setLocation(location);
        return profile;
    }

    public ProfileResponse toResponse(Profile profile) {
        ProfileResponse.LocationSummary locationSummary = null;
        if (profile.getLocation() != null) {
            Location loc = profile.getLocation();
            locationSummary = new ProfileResponse.LocationSummary(
                loc.getId(),
                loc.getCity(),
                loc.getState(),
                loc.getZip()
            );
        }

        return new ProfileResponse(
            profile.getId(),
            profile.getName(),
            profile.getEmail(),
            profile.getRole(),
            profile.getUser() != null ? profile.getUser().getId() : null,
            locationSummary
        );
    }

    public void updateEntity(Profile profile, ProfileUpdateRequest request, Location location) {
        if (request.name() != null) {
            profile.setName(request.name());
        }
        if (request.email() != null) {
            profile.setEmail(request.email());
        }
        if (location != null) {
            profile.setLocation(location);
        }
    }
}
