package com.handydandy.handyman_api.payment.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCreateRequest(
    @NotNull(message = "Booking ID is required")
    UUID bookingId,

    @NotNull(message = "Payer ID is required")
    UUID payerId,

    @NotNull(message = "Payee ID is required")
    UUID payeeId,

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be positive")
    BigDecimal amount,

    @NotBlank(message = "Payment method is required")
    String paymentMethod
) {}
