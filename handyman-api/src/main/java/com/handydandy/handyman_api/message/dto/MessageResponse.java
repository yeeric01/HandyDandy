package com.handydandy.handyman_api.message.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse(
    UUID id,
    ProfileSummary sender,
    ProfileSummary receiver,
    UUID bookingId,
    String content,
    LocalDateTime sentAt,
    LocalDateTime readAt,
    boolean deleted
) {
    public record ProfileSummary(UUID id, String name, String email) {}
}
