package com.keeb.paymentservice.configuration;

import com.keeb.paymentservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("user-service")
public interface UserFeignClient {

    @GetMapping("v1/user/fetch/{emailId}")
    public ResponseEntity<User> fetchUser(@PathVariable String emailId);

    @PutMapping("v1/user/update")
    public ResponseEntity<String> updateUser(@RequestBody User user);

}
