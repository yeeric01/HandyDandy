package com.handydandy.handyman_api.profile;

import jakarta.persistence.*;
import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.user.AppUser;

import java.util.UUID;

@Entity
public class Profile {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // You might not need role here if it's already on User
    private String role;

    // ----- Relationships -----

    // Profile owns the FK to User (1-to-1)
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    // Many profiles can point to the same location
    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    // ----- Constructors -----

    public Profile() {}

    public Profile(String name, String email, String role, AppUser user, Location location) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.user = user;
        this.location = location;
    }

    // ----- Getters & Setters -----

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
}