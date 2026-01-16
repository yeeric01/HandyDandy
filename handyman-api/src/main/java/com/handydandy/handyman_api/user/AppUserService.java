package com.handydandy.handyman_api.user;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AppUserService {

    private final AppUserRepository userRepository;

    public AppUserService(AppUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a new user
    public AppUser createUser(AppUser user) {
        return userRepository.save(user);
    }

    // Get all users
    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<AppUser> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    // Update a user
    public AppUser updateUser(UUID id, AppUser updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setEmail(updatedUser.getEmail());
            user.setPasswordHash(updatedUser.getPasswordHash());
            user.setRole(updatedUser.getRole());
            return userRepository.save(user);
        }).orElse(null);
    }

    // Delete a user
    public boolean deleteUser(UUID id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    // Optional: find by email (useful for login)
    public Optional<AppUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}