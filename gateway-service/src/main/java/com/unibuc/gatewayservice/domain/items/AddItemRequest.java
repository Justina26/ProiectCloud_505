package com.unibuc.gatewayservice.domain.items;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddItemRequest {

    private String name;
    private String description;
    private String price;
    private String categoryName;
}
