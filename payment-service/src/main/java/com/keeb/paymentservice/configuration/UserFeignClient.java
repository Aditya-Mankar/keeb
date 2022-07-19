package com.keeb.paymentservice.configuration;

import com.keeb.paymentservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-feign-client", url = "http://localhost:8083/v1/user")
public interface UserFeignClient {

    @GetMapping("/fetch/{emailId}")
    public ResponseEntity<User> fetchUser(@PathVariable String emailId);

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user);

}
