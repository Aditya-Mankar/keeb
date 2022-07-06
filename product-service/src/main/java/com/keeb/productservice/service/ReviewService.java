package com.keeb.productservice.service;

import com.keeb.productservice.model.Review;
import com.keeb.productservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ResponseEntity<String> addReview(Review review) {
        reviewRepository.save(review);
        return ResponseEntity.ok("Review added");
    }

    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    public ResponseEntity<String> updateReview(Review review) {
        reviewRepository.save(review);
        return ResponseEntity.ok("Review updated");
    }

    public ResponseEntity<String> deleteReview(Long id) {
        reviewRepository.deleteById(id);
        return ResponseEntity.ok("Review updated");
    }

}
