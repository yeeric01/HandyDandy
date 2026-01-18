package com.handydandy.handyman_api.servicerequest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record ServiceRequestCreateRequest(
    @NotNull(message = "Customer ID is required")
    UUID customerId,

    UUID handymanId,

    @NotNull(message = "Location ID is required")
    UUID locationId,

    @NotBlank(message = "Description is required")
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    String description
) {}
