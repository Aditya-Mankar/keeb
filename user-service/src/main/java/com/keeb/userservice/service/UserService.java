package com.keeb.userservice.service;

import com.keeb.userservice.configuration.ProductFeignClient;
import com.keeb.userservice.model.Product;
import com.keeb.userservice.model.User;
import com.keeb.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final ProductFeignClient productFeignClient;

    public ResponseEntity<User> fetchUser(String emailId) {
        Optional<User> user = userRepository.findByEmailId(emailId);

        log.info("Fetching user with emailId: " + emailId);

        return ResponseEntity.ok(user.orElse(null));
    }

    public ResponseEntity<String> createUser(User user) {
        userRepository.save(user);

        log.info("Saving user with emailId: " + user.getEmailId());

        return ResponseEntity.ok("User created");
    }

    public ResponseEntity<String> updateUser(User user) {
        userRepository.save(user);

        log.info("Updating user with emailId: " + user.getEmailId());

        return ResponseEntity.ok("User updated");
    }

    public ResponseEntity<String> deleteUser(String emailId) {
        userRepository.deleteByEmailId(emailId);

        log.info("Deleting user with emailId: " + emailId);

        return ResponseEntity.ok("User deleted");
    }

    public ResponseEntity<String> addProductToWishlist(String emailId, Long productId) {
        Optional<User> user = userRepository.findByEmailId(emailId);

        ResponseEntity<List<Product>> productsResponse = productFeignClient.fetchProducts(List.of(productId));
        List<Product> products = productsResponse.getBody();

        user.ifPresent(us -> {
            List<Product> wishlist = us.getWishlist();
            wishlist.add(products.get(0));
            us.setWishlist(wishlist);

            userRepository.save(us);
        });

        log.info("Added product with id: " + productId + " to wishlist of user with emailId: " + emailId);

        return ResponseEntity.ok("Product added to wishlist");
    }

    public ResponseEntity<String> removeProductFromWishlist(String emailId, Long productId) {
        Optional<User> user = userRepository.findByEmailId(emailId);

        user.ifPresent(us -> {
            List<Product> wishlist = us.getWishlist();

            List<Product> updatedWishlist = wishlist.stream()
                    .filter(pro -> !pro.getId().equals(productId))
                    .collect(Collectors.toList());

            us.setWishlist(updatedWishlist);

            userRepository.save(us);
        });

        log.info("Removing product with id: " + productId + " to wishlist of user with emailId: " + emailId);

        return ResponseEntity.ok("Product removed from wishlist");
    }

}
