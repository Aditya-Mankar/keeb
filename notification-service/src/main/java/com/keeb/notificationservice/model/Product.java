package com.keeb.notificationservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double rating;
    private String type;
    private Integer quantity;

}
