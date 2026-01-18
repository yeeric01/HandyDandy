package com.handydandy.handyman_api.handymanservice;

import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.profile.Profile;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "handyman_service", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"handyman_id", "category_id"})
})
public class HandymanService {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "handyman_id", nullable = false)
    private Profile handyman;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private ServiceCategory category;

    @Column(name = "hourly_rate", nullable = false, precision = 10, scale = 2)
    private BigDecimal hourlyRate;

    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(columnDefinition = "TEXT")
    private String certifications;

    @Column(nullable = false)
    private boolean active = true;

    public HandymanService() {}

    public HandymanService(Profile handyman, ServiceCategory category, BigDecimal hourlyRate) {
        this.handyman = handyman;
        this.category = category;
        this.hourlyRate = hourlyRate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Profile getHandyman() {
        return handyman;
    }

    public void setHandyman(Profile handyman) {
        this.handyman = handyman;
    }

    public ServiceCategory getCategory() {
        return category;
    }

    public void setCategory(ServiceCategory category) {
        this.category = category;
    }

    public BigDecimal getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(BigDecimal hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public Integer getYearsExperience() {
        return yearsExperience;
    }

    public void setYearsExperience(Integer yearsExperience) {
        this.yearsExperience = yearsExperience;
    }

    public String getCertifications() {
        return certifications;
    }

    public void setCertifications(String certifications) {
        this.certifications = certifications;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
