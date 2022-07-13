package com.keeb.userservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
public class ProductInventory {

    private Long productId;
    private Integer quantity;

}
