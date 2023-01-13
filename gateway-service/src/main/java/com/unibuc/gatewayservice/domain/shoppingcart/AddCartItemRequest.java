package com.unibuc.gatewayservice.domain.shoppingcart;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCartItemRequest {

    private String itemId;
    private String userId;
    private String quantity;
}
