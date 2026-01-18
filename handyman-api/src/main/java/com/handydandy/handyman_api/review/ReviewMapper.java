package com.handydandy.handyman_api.review;

import com.handydandy.handyman_api.booking.Booking;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.review.dto.ReviewCreateRequest;
import com.handydandy.handyman_api.review.dto.ReviewResponse;
import com.handydandy.handyman_api.review.dto.ReviewUpdateRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper {

    public Review toEntity(ReviewCreateRequest request, Profile reviewer, Profile handyman, Booking booking) {
        Review review = new Review();
        review.setReviewer(reviewer);
        review.setHandyman(handyman);
        review.setBooking(booking);
        review.setRating(request.rating());
        review.setComment(request.comment());
        return review;
    }

    public ReviewResponse toResponse(Review review) {
        ReviewResponse.ProfileSummary reviewerSummary = null;
        if (review.getReviewer() != null) {
            Profile r = review.getReviewer();
            reviewerSummary = new ReviewResponse.ProfileSummary(r.getId(), r.getName(), r.getEmail());
        }

        ReviewResponse.ProfileSummary handymanSummary = null;
        if (review.getHandyman() != null) {
            Profile h = review.getHandyman();
            handymanSummary = new ReviewResponse.ProfileSummary(h.getId(), h.getName(), h.getEmail());
        }

        return new ReviewResponse(
            review.getId(),
            reviewerSummary,
            handymanSummary,
            review.getBooking() != null ? review.getBooking().getId() : null,
            review.getRating(),
            review.getComment(),
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }

    public void updateEntity(Review review, ReviewUpdateRequest request) {
        if (request.rating() != null) {
            review.setRating(request.rating());
        }
        if (request.comment() != null) {
            review.setComment(request.comment());
        }
        review.setUpdatedAt(LocalDateTime.now());
    }
}
