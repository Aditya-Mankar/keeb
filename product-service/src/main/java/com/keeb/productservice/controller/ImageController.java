package com.keeb.productservice.controller;

import com.keeb.productservice.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/product/image")
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadFile(@PathVariable Long id,
                                             @RequestParam("file") MultipartFile file) throws Exception {
        return imageService.saveImage(id, file);
    }

}
