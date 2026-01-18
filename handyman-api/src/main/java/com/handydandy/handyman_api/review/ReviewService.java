package com.handydandy.handyman_api.review;

import com.handydandy.handyman_api.booking.Booking;
import com.handydandy.handyman_api.booking.BookingRepository;
import com.handydandy.handyman_api.exception.DuplicateResourceException;
import com.handydandy.handyman_api.exception.ResourceNotFoundException;
import com.handydandy.handyman_api.profile.Profile;
import com.handydandy.handyman_api.profile.ProfileRepository;
import com.handydandy.handyman_api.review.dto.ReviewCreateRequest;
import com.handydandy.handyman_api.review.dto.ReviewResponse;
import com.handydandy.handyman_api.review.dto.ReviewStatsResponse;
import com.handydandy.handyman_api.review.dto.ReviewUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProfileRepository profileRepository;
    private final BookingRepository bookingRepository;
    private final ReviewMapper mapper;

    public ReviewService(ReviewRepository reviewRepository,
                         ProfileRepository profileRepository,
                         BookingRepository bookingRepository,
                         ReviewMapper mapper) {
        this.reviewRepository = reviewRepository;
        this.profileRepository = profileRepository;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    public ReviewResponse createReview(ReviewCreateRequest request) {
        if (reviewRepository.findByBookingId(request.bookingId()).isPresent()) {
            throw new DuplicateResourceException("Review for booking already exists");
        }

        Profile reviewer = profileRepository.findById(request.reviewerId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.reviewerId()));

        Profile handyman = profileRepository.findById(request.handymanId())
            .orElseThrow(() -> new ResourceNotFoundException("Profile", "id", request.handymanId()));

        Booking booking = bookingRepository.findById(request.bookingId())
            .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", request.bookingId()));

        Review review = mapper.toEntity(request, reviewer, handyman, booking);
        return mapper.toResponse(reviewRepository.save(review));
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getAllReviews(Pageable pageable) {
        return reviewRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsByHandyman(UUID handymanId, Pageable pageable) {
        return reviewRepository.findByHandymanId(handymanId, pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ReviewResponse getReviewById(UUID id) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        return mapper.toResponse(review);
    }

    @Transactional(readOnly = true)
    public ReviewStatsResponse getHandymanStats(UUID handymanId) {
        Double avgRating = reviewRepository.getAverageRatingForHandyman(handymanId);
        Long count = reviewRepository.getReviewCountForHandyman(handymanId);
        return new ReviewStatsResponse(handymanId, avgRating, count);
    }

    public ReviewResponse updateReview(UUID id, ReviewUpdateRequest request) {
        Review review = reviewRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));
        mapper.updateEntity(review, request);
        return mapper.toResponse(reviewRepository.save(review));
    }

    public void deleteReview(UUID id) {
        if (!reviewRepository.existsById(id)) {
            throw new ResourceNotFoundException("Review", "id", id);
        }
        reviewRepository.deleteById(id);
    }
}
