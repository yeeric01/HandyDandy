package com.handydandy.handyman_api.category.dto;

import jakarta.validation.constraints.Size;

public record ServiceCategoryUpdateRequest(
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    String name,

    String description,

    String iconUrl,

    Boolean active
) {}
