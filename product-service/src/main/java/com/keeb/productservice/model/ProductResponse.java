package com.keeb.productservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.core.io.Resource;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double rating;
    private String type;
    private List<Review> reviewsList;
    private byte[] image;

}
