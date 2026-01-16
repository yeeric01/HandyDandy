package com.handydandy.handyman_api.profile;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    public ProfileService(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    // Create a new profile
    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    // Get all profiles
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    // Get profile by ID
    public Optional<Profile> getProfileById(UUID id) {
        return profileRepository.findById(id);
    }

    // Update profile
    public Profile updateProfile(UUID id, Profile updatedProfile) {
        return profileRepository.findById(id).map(profile -> {
            profile.setName(updatedProfile.getName());
            profile.setLocation(updatedProfile.getLocation());
            profile.setUser(updatedProfile.getUser());
            return profileRepository.save(profile);
        }).orElse(null);
    }

    // Delete profile
    public boolean deleteProfile(UUID id) {
        return profileRepository.findById(id).map(profile -> {
            profileRepository.delete(profile);
            return true;
        }).orElse(false);
    }

    // Get all handymen (assuming user role HANDYMAN)
    public List<Profile> getAllHandymen() {
        return profileRepository.findAll().stream()
                .filter(profile -> profile.getUser().getRole() == com.handydandy.handyman_api.user.AppUser.Role.HANDYMAN)
                .toList();
    }
}