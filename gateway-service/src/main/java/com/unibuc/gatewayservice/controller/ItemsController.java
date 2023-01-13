package com.unibuc.gatewayservice.controller;



import com.unibuc.gatewayservice.domain.items.AddCategoryRequest;
import com.unibuc.gatewayservice.domain.items.AddItemRequest;
import com.unibuc.gatewayservice.domain.items.CategoryDto;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import com.unibuc.gatewayservice.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {

    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> getItems(@RequestParam(value = "itemId", required = false) String itemId){
        log.info("Attempting to get all items");
        return itemService.findAllItems(itemId);
    }
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(){
        log.info("Attempting to get all items");
        return itemService.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody AddItemRequest addItemRequest){
        log.info("Attempting to create item with name: {}", addItemRequest.getName());
        return itemService.createItem(addItemRequest);
    }
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody AddCategoryRequest addCategoryRequest){
        log.info("Attempting to create category with name: {}", addCategoryRequest.getName());
        return itemService.createCategory(addCategoryRequest);
    }

}
