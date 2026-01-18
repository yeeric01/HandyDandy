package com.handydandy.handyman_api.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {

    Page<Review> findByHandymanId(UUID handymanId, Pageable pageable);

    Page<Review> findByReviewerId(UUID reviewerId, Pageable pageable);

    Optional<Review> findByBookingId(UUID bookingId);

    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.handyman.id = :handymanId")
    Double getAverageRatingForHandyman(@Param("handymanId") UUID handymanId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.handyman.id = :handymanId")
    Long getReviewCountForHandyman(@Param("handymanId") UUID handymanId);
}
