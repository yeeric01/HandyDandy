package com.handydandy.handyman_api.location.dto;

import jakarta.validation.constraints.Pattern;

public record LocationUpdateRequest(
    String city,
    String state,

    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Invalid zip code format")
    String zip
) {}
