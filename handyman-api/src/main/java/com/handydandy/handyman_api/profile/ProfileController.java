package com.handydandy.handyman_api.profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    // Get all profiles
    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    // Get profile by ID
    @GetMapping("/{id}")
    public Optional<Profile> getProfileById(@PathVariable UUID id) {
        return profileService.getProfileById(id);
    }

    // Create a new profile
    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        return profileService.createProfile(profile);
    }

    // Update a profile
    @PutMapping("/{id}")
    public Profile updateProfile(@PathVariable UUID id, @RequestBody Profile profile) {
        return profileService.updateProfile(id, profile);
    }

    // Delete a profile
    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable UUID id) {
        profileService.deleteProfile(id);
    }

    // Get all handymen
    @GetMapping("/handymen")
    public List<Profile> getAllHandymen() {
        return profileService.getAllHandymen();
    }
}