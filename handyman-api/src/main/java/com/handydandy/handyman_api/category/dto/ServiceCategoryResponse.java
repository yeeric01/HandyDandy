package com.handydandy.handyman_api.category.dto;

import java.util.UUID;

public record ServiceCategoryResponse(
    UUID id,
    String name,
    String description,
    String iconUrl,
    boolean active
) {}
