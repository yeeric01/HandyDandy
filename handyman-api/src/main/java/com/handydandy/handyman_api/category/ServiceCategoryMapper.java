package com.handydandy.handyman_api.category;

import com.handydandy.handyman_api.category.dto.ServiceCategoryCreateRequest;
import com.handydandy.handyman_api.category.dto.ServiceCategoryResponse;
import com.handydandy.handyman_api.category.dto.ServiceCategoryUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class ServiceCategoryMapper {

    public ServiceCategory toEntity(ServiceCategoryCreateRequest request) {
        ServiceCategory category = new ServiceCategory();
        category.setName(request.name());
        category.setDescription(request.description());
        category.setIconUrl(request.iconUrl());
        return category;
    }

    public ServiceCategoryResponse toResponse(ServiceCategory category) {
        return new ServiceCategoryResponse(
            category.getId(),
            category.getName(),
            category.getDescription(),
            category.getIconUrl(),
            category.isActive()
        );
    }

    public void updateEntity(ServiceCategory category, ServiceCategoryUpdateRequest request) {
        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.description() != null) {
            category.setDescription(request.description());
        }
        if (request.iconUrl() != null) {
            category.setIconUrl(request.iconUrl());
        }
        if (request.active() != null) {
            category.setActive(request.active());
        }
    }
}
