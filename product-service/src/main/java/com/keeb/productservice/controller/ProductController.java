package com.keeb.productservice.controller;

import com.keeb.productservice.model.Product;
import com.keeb.productservice.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/get-all-products")
    public ResponseEntity<Object> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/fetch")
    public ResponseEntity<List<Product>> fetchProducts(@RequestBody List<Long> productIds) {
        return productService.fetchProducts(productIds);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return productService.deleteProduct(id);
    }

}
