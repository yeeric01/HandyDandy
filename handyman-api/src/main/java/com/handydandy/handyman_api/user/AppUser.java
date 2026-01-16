package com.handydandy.handyman_api.user;

import jakarta.persistence.*;
import com.handydandy.handyman_api.profile.Profile;
import java.util.UUID;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        CUSTOMER,
        HANDYMAN
    }

    // ----- Optional bidirectional Profile -----
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Profile profile;

    // ----- Constructors -----
    public AppUser() {}

    public AppUser(String email, String passwordHash, Role role) {
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // ----- Getters & Setters -----
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public Profile getProfile() { return profile; }
    public void setProfile(Profile profile) { this.profile = profile; }
}
