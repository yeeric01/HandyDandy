package com.handydandy.handyman_api.servicerequest.dto;

import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ServiceRequestUpdateRequest(
    UUID handymanId,

    UUID locationId,

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    String description,

    String status
) {}
