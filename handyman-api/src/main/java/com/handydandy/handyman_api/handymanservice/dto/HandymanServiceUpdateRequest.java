package com.handydandy.handyman_api.handymanservice.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;

public record HandymanServiceUpdateRequest(
    @DecimalMin(value = "0.01", message = "Hourly rate must be positive")
    BigDecimal hourlyRate,

    @Min(value = 0, message = "Years of experience cannot be negative")
    Integer yearsExperience,

    String certifications,

    Boolean active
) {}
