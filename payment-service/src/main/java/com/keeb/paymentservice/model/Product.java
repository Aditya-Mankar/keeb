package com.keeb.paymentservice.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private Double rating;
    private String type;

}
