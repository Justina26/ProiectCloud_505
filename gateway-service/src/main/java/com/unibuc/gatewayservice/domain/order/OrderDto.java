package com.unibuc.gatewayservice.domain.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private String userId;
    private String price;
    private List<OrderedItemDto> orderedItems;
}
