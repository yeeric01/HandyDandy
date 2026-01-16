package com.handydandy.handyman_api.profile;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {
     // Find by email
    Optional<Profile> findByEmail(String email);

    // Find all handymen by role
    List<Profile> findByRole(String role);
}