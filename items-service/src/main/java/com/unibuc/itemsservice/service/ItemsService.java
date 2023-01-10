package com.unibuc.itemsservice.service;

import com.unibuc.itemsservice.domain.dto.AddItemRequest;
import com.unibuc.itemsservice.domain.dto.ItemDto;
import com.unibuc.itemsservice.domain.entity.Item;
import com.unibuc.itemsservice.domain.mapper.ItemsMapper;
import com.unibuc.itemsservice.repository.ItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemsService {

    private final ItemsMapper itemsMapper;
    private final CategoryService categoryService;
    private final ItemsRepository itemsRepository;

    public List<ItemDto> findAllItems(){
        return itemsRepository.findAll()
                .stream()
                .map(itemsMapper::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto findById(String itemId) {
        return itemsRepository.findById(Long.valueOf(itemId))
                .map(itemsMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Item not found for id: " + itemId));
    }

    public void createItem(AddItemRequest addItemRequest) {
        var item = Item.builder()
                .name(addItemRequest.getName())
                .description(addItemRequest.getDescription())
                .price(Integer.valueOf(addItemRequest.getPrice()))
                .category(categoryService.findCategoryByName(addItemRequest.getCategoryName()))
                .build();

        itemsRepository.save(item);

    }

    public ItemDto updateItem(String itemId, String quantity) {
        var itemOptional = itemsRepository.findById(Long.valueOf(itemId));

        var item = itemOptional.orElseThrow(() -> new RuntimeException("Item not found for ID: " +itemId));

        item.setQuantity(item.getQuantity() - Integer.parseInt(quantity));

        return itemsMapper.toDto(itemsRepository.save(item));

    }
}
