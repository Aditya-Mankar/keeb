package com.keeb.notificationservice.configuration;

import com.keeb.notificationservice.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("product-service")
public interface ProductFeignClient {

    @PostMapping("v1/product/fetch")
    public ResponseEntity<List<Product>> fetchProducts(@RequestBody List<Long> productIds);

}
