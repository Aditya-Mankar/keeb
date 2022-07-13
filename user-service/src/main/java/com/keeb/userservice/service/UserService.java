package com.keeb.userservice.service;

import com.keeb.userservice.model.User;
import com.keeb.userservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public ResponseEntity<User> fetchUser(String emailId) {
        Optional<User> user = userRepository.findByEmailId(emailId);

        return ResponseEntity.ok(user.orElse(null));
    }

    public ResponseEntity<String> createUser(User user) {
        userRepository.save(user);

        return ResponseEntity.ok("User created");
    }

    public ResponseEntity<String> updateUser(User user) {
        userRepository.save(user);

        return ResponseEntity.ok("User updated");
    }

    public ResponseEntity<String> deleteUser(String emailId) {
        userRepository.deleteByEmailId(emailId);

        return ResponseEntity.ok("User deleted");
    }

}
