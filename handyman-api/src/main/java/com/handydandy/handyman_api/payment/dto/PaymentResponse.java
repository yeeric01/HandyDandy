package com.handydandy.handyman_api.payment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
    UUID id,
    UUID bookingId,
    ProfileSummary payer,
    ProfileSummary payee,
    BigDecimal amount,
    String paymentMethod,
    String status,
    String transactionId,
    LocalDateTime createdAt,
    LocalDateTime processedAt
) {
    public record ProfileSummary(UUID id, String name, String email) {}
}
