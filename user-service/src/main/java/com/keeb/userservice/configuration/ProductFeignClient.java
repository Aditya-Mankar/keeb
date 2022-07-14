package com.keeb.userservice.configuration;

import com.keeb.userservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-feign-client", url = "http://localhost:8080/v1/product")
public interface ProductFeignClient {

    @PostMapping("/fetch")
    public ResponseEntity<List<Product>> fetchProducts(@RequestBody List<Long> productIds);

}
