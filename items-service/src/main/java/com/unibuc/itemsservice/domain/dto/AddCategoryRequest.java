package com.unibuc.itemsservice.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddCategoryRequest {

    private String name;
    private String description;
}
