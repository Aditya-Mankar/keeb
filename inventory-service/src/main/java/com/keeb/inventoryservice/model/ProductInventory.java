package com.keeb.inventoryservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductInventory {

    private Long productId;
    private Integer quantity;

}
