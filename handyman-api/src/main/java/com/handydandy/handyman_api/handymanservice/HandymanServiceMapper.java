package com.handydandy.handyman_api.handymanservice;

import com.handydandy.handyman_api.category.ServiceCategory;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceCreateRequest;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceResponse;
import com.handydandy.handyman_api.handymanservice.dto.HandymanServiceUpdateRequest;
import com.handydandy.handyman_api.profile.Profile;
import org.springframework.stereotype.Component;

@Component
public class HandymanServiceMapper {

    public HandymanService toEntity(HandymanServiceCreateRequest request, Profile handyman, ServiceCategory category) {
        HandymanService service = new HandymanService();
        service.setHandyman(handyman);
        service.setCategory(category);
        service.setHourlyRate(request.hourlyRate());
        service.setYearsExperience(request.yearsExperience());
        service.setCertifications(request.certifications());
        return service;
    }

    public HandymanServiceResponse toResponse(HandymanService service) {
        HandymanServiceResponse.ProfileSummary handymanSummary = null;
        if (service.getHandyman() != null) {
            Profile h = service.getHandyman();
            handymanSummary = new HandymanServiceResponse.ProfileSummary(h.getId(), h.getName(), h.getEmail());
        }

        HandymanServiceResponse.CategorySummary categorySummary = null;
        if (service.getCategory() != null) {
            ServiceCategory c = service.getCategory();
            categorySummary = new HandymanServiceResponse.CategorySummary(c.getId(), c.getName());
        }

        return new HandymanServiceResponse(
            service.getId(),
            handymanSummary,
            categorySummary,
            service.getHourlyRate(),
            service.getYearsExperience(),
            service.getCertifications(),
            service.isActive()
        );
    }

    public void updateEntity(HandymanService service, HandymanServiceUpdateRequest request) {
        if (request.hourlyRate() != null) {
            service.setHourlyRate(request.hourlyRate());
        }
        if (request.yearsExperience() != null) {
            service.setYearsExperience(request.yearsExperience());
        }
        if (request.certifications() != null) {
            service.setCertifications(request.certifications());
        }
        if (request.active() != null) {
            service.setActive(request.active());
        }
    }
}
