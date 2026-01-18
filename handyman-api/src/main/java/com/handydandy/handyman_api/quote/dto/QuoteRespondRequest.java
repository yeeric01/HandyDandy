package com.handydandy.handyman_api.quote.dto;

import jakarta.validation.constraints.NotBlank;

public record QuoteRespondRequest(
    @NotBlank(message = "Response action is required (ACCEPT or REJECT)")
    String action
) {}
