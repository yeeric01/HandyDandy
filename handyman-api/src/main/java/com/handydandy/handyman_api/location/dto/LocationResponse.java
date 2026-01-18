package com.handydandy.handyman_api.location.dto;

import java.util.UUID;

public record LocationResponse(
    UUID id,
    String city,
    String state,
    String zip
) {}
