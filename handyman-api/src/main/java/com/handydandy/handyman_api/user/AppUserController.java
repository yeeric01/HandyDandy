package com.handydandy.handyman_api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/app-users")
public class AppUserController {

    @Autowired
    private AppUserService userService;

    // Get all users
    @GetMapping
    public List<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get user by ID
    @GetMapping("/{id}")
    public Optional<AppUser> getUserById(@PathVariable UUID id) {
        return userService.getUserById(id);
    }

    // Create a new user
    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return userService.createUser(user);
    }

    // Update a user
    @PutMapping("/{id}")
    public AppUser updateUser(@PathVariable UUID id, @RequestBody AppUser user) {
        return userService.updateUser(id, user);
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    // Optional: get user by email
    @GetMapping("/by-email")
    public Optional<AppUser> getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email);
    }
}