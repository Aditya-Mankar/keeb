package com.keeb.productservice.service;

import com.keeb.productservice.model.Image;
import com.keeb.productservice.model.Product;
import com.keeb.productservice.model.ProductResponse;
import com.keeb.productservice.model.Review;
import com.keeb.productservice.repository.ImageRepository;
import com.keeb.productservice.repository.ProductRepository;
import com.keeb.productservice.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;

    public ResponseEntity<Object> getAllProducts() {
        List<Product> productsList = productRepository.findAll();
        List<ProductResponse> response = new ArrayList<>();

        productsList.forEach(product -> {
            List<Review> reviews = reviewRepository.findByProductId(product.getId());
            Optional<Image> image = imageRepository.findByProductId(product.getId());

            ProductResponse productResponse = transformToProductResponse(product, reviews, image);

            response.add(productResponse);
        });

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> addProduct(Product product) {
        productRepository.save(product);

        return ResponseEntity.ok("Product added successfully");
    }

    public ResponseEntity<Object> getProductById(Long id) {
        Product product = productRepository.findByProductId(id)
                .orElseThrow(() -> new RuntimeException("No product found with id: " + id));

        List<Review> reviews = reviewRepository.findByProductId(product.getId());
        Optional<Image> image = imageRepository.findByProductId(product.getId());

        ProductResponse productResponse = transformToProductResponse(product, reviews, image);

        return ResponseEntity.ok(productResponse);
    }

    public ResponseEntity<List<Product>> fetchProducts(List<Long> productIds) {
        List<Product> products = productRepository.fetchProducts(productIds);

        return ResponseEntity.ok(products);
    }

    public ResponseEntity<String> updateProduct(Product product) {
        if(product.getId() == null)
            return ResponseEntity.badRequest().body("Id cannot be null");

        productRepository.save(product);

        return ResponseEntity.ok("Product updated successfully");
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        reviewRepository.deleteByProductId(id);
        productRepository.deleteById(id);

        return ResponseEntity.ok("Product deleted successfully");
    }

    public Double calculateRating(List<Review> reviews) {

        int totalRating = reviews.stream()
                .mapToInt(Review::getRating)
                .sum();

        return (double) totalRating / reviews.size();
    }

    public ProductResponse transformToProductResponse(Product product, List<Review> reviews, Optional<Image> image) {
        byte[] base64EncodedData = image.isPresent() ? Base64.getEncoder().encode(image.get().getData()) : null;

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .rating(calculateRating(reviews))
                .type(product.getType())
                .reviewsList(reviews)
                .image(base64EncodedData)
                .build();

        return productResponse;
    }

}
