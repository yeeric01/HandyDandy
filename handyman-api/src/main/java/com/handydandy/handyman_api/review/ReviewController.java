package com.handydandy.handyman_api.review;

import com.handydandy.handyman_api.review.dto.ReviewCreateRequest;
import com.handydandy.handyman_api.review.dto.ReviewResponse;
import com.handydandy.handyman_api.review.dto.ReviewStatsResponse;
import com.handydandy.handyman_api.review.dto.ReviewUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<Page<ReviewResponse>> getAllReviews(
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviews(pageable));
    }

    @GetMapping("/handyman/{handymanId}")
    public ResponseEntity<Page<ReviewResponse>> getReviewsByHandyman(
            @PathVariable UUID handymanId,
            @PageableDefault(size = 20, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(reviewService.getReviewsByHandyman(handymanId, pageable));
    }

    @GetMapping("/handyman/{handymanId}/stats")
    public ResponseEntity<ReviewStatsResponse> getHandymanStats(@PathVariable UUID handymanId) {
        return ResponseEntity.ok(reviewService.getHandymanStats(handymanId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewResponse> getReviewById(@PathVariable UUID id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> createReview(
            @Valid @RequestBody ReviewCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(reviewService.createReview(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponse> updateReview(
            @PathVariable UUID id,
            @Valid @RequestBody ReviewUpdateRequest request) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable UUID id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
