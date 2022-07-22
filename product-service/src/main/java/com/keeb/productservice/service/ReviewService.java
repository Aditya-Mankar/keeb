package com.keeb.productservice.service;

import com.keeb.productservice.model.Review;
import com.keeb.productservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ResponseEntity<String> addReview(Review review) {
        reviewRepository.save(review);

        log.info("Creating review for product with id: " + review.getProductId());

        return ResponseEntity.ok("Review added");
    }

    public List<Review> getReviewsByProductId(Long productId) {
        log.info("Fetching reviews with product id: " + productId);

        return reviewRepository.findByProductId(productId);
    }

    public ResponseEntity<String> updateReview(Review review) {
        reviewRepository.save(review);

        log.info("Updating review for product with id: " + review.getProductId());

        return ResponseEntity.ok("Review updated");
    }

    public ResponseEntity<String> deleteReview(Long id) {
        reviewRepository.deleteById(id);

        log.info("Deleting review for product with id: " + id);

        return ResponseEntity.ok("Review updated");
    }

}
