package com.handydandy.handyman_api.message.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public record MessageCreateRequest(
    @NotNull(message = "Sender ID is required")
    UUID senderId,

    @NotNull(message = "Receiver ID is required")
    UUID receiverId,

    UUID bookingId,

    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content cannot exceed 2000 characters")
    String content
) {}
