package com.keeb.notificationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class ProductInventory {

    private Long productId;
    private Integer quantity;

}
