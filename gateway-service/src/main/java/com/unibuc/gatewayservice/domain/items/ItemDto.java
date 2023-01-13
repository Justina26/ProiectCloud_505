package com.unibuc.gatewayservice.domain.items;

import lombok.*;

@Data
public class ItemDto {

    private Long id;
    private String name;
    private String description;
    private String price;
    private String quantity;
    private CategoryDto category;

}
