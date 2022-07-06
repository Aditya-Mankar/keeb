package com.keeb.productservice.service;

import com.keeb.productservice.model.Image;
import com.keeb.productservice.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public ResponseEntity<String> saveImage(Long productId, MultipartFile file) throws IOException {
        Image image = Image.builder()
                .productId(productId)
                .fileType(file.getContentType())
                .fileName(StringUtils.cleanPath(file.getOriginalFilename()))
                .data(file.getBytes())
                .build();

        imageRepository.save(image);

        return ResponseEntity.ok("Image saved");
    }

}
