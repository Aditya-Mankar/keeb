package com.keeb.userservice.controller;

import com.keeb.userservice.model.User;
import com.keeb.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/fetch/{emailId}")
    public ResponseEntity<User> fetchUser(@PathVariable String emailId) {
        return userService.fetchUser(emailId);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete/{emailId}")
    public ResponseEntity<String> deleteUser(@PathVariable String emailId) {
        return userService.deleteUser(emailId);
    }

    @PostMapping("/wishlist/add/{emailId}/{productId}")
    public ResponseEntity<String> addProductToWishlist(@PathVariable String emailId, @PathVariable Long productId) {
        return userService.addProductToWishlist(emailId, productId);
    }

    @DeleteMapping("/wishlist/delete/{emailId}/{productId}")
    public ResponseEntity<String> removeProductFromWishlist(@PathVariable String emailId, @PathVariable Long productId) {
        return userService.removeProductFromWishlist(emailId, productId);
    }

}
