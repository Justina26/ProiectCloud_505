package com.unibuc.gatewayservice.service;

import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.domain.items.AddCategoryRequest;
import com.unibuc.gatewayservice.domain.items.AddItemRequest;
import com.unibuc.gatewayservice.domain.items.CategoryDto;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemServiceClient itemServiceClient;
    public List<ItemDto> findAllItems(String itemId) {
        var items = itemServiceClient.getAllItemsRequest();

        return Objects.isNull(itemId) ? items : items
                        .stream()
                        .filter(itemDto -> itemDto.getId().equals(Long.valueOf(itemId)))
                        .collect(Collectors.toList());
    }

    public List<CategoryDto> findAllCategories(){
        return itemServiceClient.getAllCategoriesRequest();
    }

    public ResponseEntity<?> createItem(AddItemRequest addItemRequest) {
        return itemServiceClient.createItemRequest(addItemRequest);

    }

    public ResponseEntity<?> createCategory(AddCategoryRequest addCategoryRequest) {
        return itemServiceClient.createCategoryRequest(addCategoryRequest);
    }


}
