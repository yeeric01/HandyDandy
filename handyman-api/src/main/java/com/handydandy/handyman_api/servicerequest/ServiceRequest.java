package com.handydandy.handyman_api.servicerequest;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import com.handydandy.handyman_api.location.Location;
import com.handydandy.handyman_api.profile.Profile;

@Entity
public class ServiceRequest {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Profile customer;

    @ManyToOne
    @JoinColumn(name = "handyman_id", nullable = false)
    private Profile handyman;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public enum Status {
        PENDING,
        ACCEPTED,
        COMPLETED,
        CANCELLED
    }

    // Constructors
    public ServiceRequest() {}

    public ServiceRequest(Profile customer, Profile handyman, Location location, String description, Status status) {
        this.customer = customer;
        this.handyman = handyman;
        this.location = location;
        this.description = description;
        this.status = status;
        this.createdAt = LocalDateTime.now();
    }

    // Getters & Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public Profile getCustomer() { return customer; }
    public void setCustomer(Profile customer) { this.customer = customer; }

    public Profile getHandyman() { return handyman; }
    public void setHandyman(Profile handyman) { this.handyman = handyman; }

    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
