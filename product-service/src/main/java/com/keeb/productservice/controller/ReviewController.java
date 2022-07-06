package com.keeb.productservice.controller;

import com.keeb.productservice.model.Product;
import com.keeb.productservice.model.Review;
import com.keeb.productservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/product/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<String> addReview(@RequestBody Review review) {
        return reviewService.addReview(review);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateReview(@RequestBody Review review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        return reviewService.deleteReview(id);
    }

}
