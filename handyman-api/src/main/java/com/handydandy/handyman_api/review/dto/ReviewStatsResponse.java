package com.handydandy.handyman_api.review.dto;

import java.util.UUID;

public record ReviewStatsResponse(
    UUID handymanId,
    Double averageRating,
    Long totalReviews
) {}
