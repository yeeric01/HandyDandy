package com.handydandy.handyman_api.location;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class Location {

    @Id
    @GeneratedValue
    private UUID id;

    private String city;
    private String state;
    private String zip;

    // Constructors
    public Location() {}

    public Location(String city, String state, String zip) {
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    // Getters & Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}