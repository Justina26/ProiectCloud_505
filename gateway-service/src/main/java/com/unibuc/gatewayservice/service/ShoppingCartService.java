package com.unibuc.gatewayservice.service;


import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.client.ShoppingCartServiceClient;
import com.unibuc.gatewayservice.domain.shoppingcart.AddCartItemRequest;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartDto;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShoppingCartService {

    private final ShoppingCartServiceClient shoppingCartServiceClient;
    private final ItemServiceClient itemServiceClient;

    public ShoppingCartDto addItem(AddCartItemRequest addCartItemRequest) {
        var item = itemServiceClient.getSingleItemByIdRequest(addCartItemRequest.getItemId()).block();

        var currentShoppingCartItems = shoppingCartServiceClient.getShoppingCartByUserId(addCartItemRequest.getUserId());

        var totalQuantity = calculateTotalQuantityInShoppingCart(currentShoppingCartItems, addCartItemRequest.getItemId());

        if (Integer.parseInt(item.getQuantity()) < Integer.parseInt(addCartItemRequest.getQuantity()) + totalQuantity)
            throw new RuntimeException("Quantity not available for this product!!");

        return shoppingCartServiceClient.addItemToShoppingCart(addCartItemRequest);
    }

    public List<ShoppingCartItemDto> getShoppingCartItemsByUserId(String userId) {
        return shoppingCartServiceClient.getShoppingCartByUserId(userId);
    }

    private Integer calculateTotalQuantityInShoppingCart(List<ShoppingCartItemDto> shoppingCartItemDtos, String itemId){
        return shoppingCartItemDtos.stream()
                .filter(shoppingCartItemDto -> shoppingCartItemDto.getItemId().equals(itemId))
                .map(ShoppingCartItemDto::getQuantity)
                .map(Integer::parseInt)
                .reduce(0, Integer::sum);
    }

    public void clearShoppingCart(String userId) {
        shoppingCartServiceClient.clearShoppingCart(userId);
    }
}
