package com.unibuc.itemsservice.controller;

import com.unibuc.itemsservice.domain.dto.AddCategoryRequest;
import com.unibuc.itemsservice.domain.dto.AddItemRequest;
import com.unibuc.itemsservice.domain.dto.CategoryDto;
import com.unibuc.itemsservice.domain.dto.ItemDto;

import com.unibuc.itemsservice.service.CategoryService;
import com.unibuc.itemsservice.service.ItemsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class ItemsController {

    private final ItemsService itemsService;
    private final CategoryService categoryService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ItemDto> getItems(){
        log.info("Return all items");
        return itemsService.findAllItems();
    }

    @GetMapping("/{itemId}")
    public ItemDto getItemById(@PathVariable(name = "itemId") String itemId){
        log.info("Return item with id: {}", itemId);
        return itemsService.findById(itemId);
    }

    @GetMapping("/categories")
    public List<CategoryDto> getAllCategories(){
        log.info("Return all categories");
        return categoryService.findAllCategories();
    }

    @PostMapping
    public ResponseEntity<?> createItem(@RequestBody AddItemRequest addItemRequest){
        log.info("Attempting to create item with name: {}", addItemRequest.getName());
        itemsService.createItem(addItemRequest);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@RequestBody AddCategoryRequest addCategoryRequest){
        log.info("Attempting to create category with name: {}", addCategoryRequest.getName());
        categoryService.createItem(addCategoryRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ItemDto updateItemQuanity(@RequestParam(name = "itemId") String itemId
                , @RequestParam(name = "quantity") String quantity){
        log.info("Attempting to update item quantity for ID: {}", itemId);
        return itemsService.updateItem(itemId, quantity);
    }


}
