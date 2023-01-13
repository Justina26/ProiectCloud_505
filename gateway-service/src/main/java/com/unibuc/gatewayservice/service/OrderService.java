package com.unibuc.gatewayservice.service;


import com.unibuc.gatewayservice.client.ItemServiceClient;
import com.unibuc.gatewayservice.client.OrderServiceClient;
import com.unibuc.gatewayservice.client.ShoppingCartServiceClient;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import com.unibuc.gatewayservice.domain.order.CreateOrderRequest;
import com.unibuc.gatewayservice.domain.order.OrderDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemsRequest;
import com.unibuc.gatewayservice.domain.shoppingcart.ShoppingCartItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderServiceClient orderServiceClient;
    private final ItemServiceClient itemServiceClient;
    private final ShoppingCartServiceClient shoppingCartServiceClient;

    public OrderDto createOrder(String userId) {

        var createOrderRequestBuilder = CreateOrderRequest.builder().userId(userId);
        var shoppingCartItems = shoppingCartServiceClient.getShoppingCartByUserId(userId);

        createOrderRequestBuilder.price(calculatePrice(shoppingCartItems).toString());
        createOrderRequestBuilder.orderedItems(createOrderedItemsRequest(shoppingCartItems));

        updateItemsQuantity(shoppingCartItems);
        shoppingCartServiceClient.clearShoppingCart(userId);

        return orderServiceClient.createOrderRequest(createOrderRequestBuilder.build());
    }

    private List<OrderedItemsRequest> createOrderedItemsRequest(List<ShoppingCartItemDto> items) {
        return items.stream()
                .map(shoppingCartItemDto -> OrderedItemsRequest
                                .builder()
                                .itemId(shoppingCartItemDto.getItemId())
                                .quantity(shoppingCartItemDto.getQuantity())
                                .build())
                .collect(Collectors.toList());
    }

    private List<ItemDto> updateItemsQuantity(List<ShoppingCartItemDto> shoppingCartItemDtos){
        return Flux.fromIterable(collectItemsTotalQuantity(shoppingCartItemDtos).entrySet())
                .flatMap(entrySet ->
                        itemServiceClient.updateItemQuantity(entrySet.getKey(), entrySet.getValue().toString()))
                .collectList()
                .block();
    }

    private Map<String, Integer> collectItemsTotalQuantity(List<ShoppingCartItemDto> shoppingCartItemDtos){
        return shoppingCartItemDtos.stream()
                .collect(Collectors.groupingBy(ShoppingCartItemDto::getItemId,
                        Collectors.mapping(ShoppingCartItemDto::getQuantity, Collectors.summingInt(Integer::valueOf))));

    }

    private Integer calculatePrice(List<ShoppingCartItemDto> shoppingCartItemDtos) {

        return Objects.requireNonNull(Flux.fromIterable(shoppingCartItemDtos)
                        .flatMap(shoppingCartItemDto -> itemServiceClient.getSingleItemByIdRequest(shoppingCartItemDto.getItemId()))
                        .map(itemDto -> Integer.parseInt(itemDto.getPrice()) * Integer.parseInt(findShoppingCartQuantity(shoppingCartItemDtos, itemDto.getId())))
                        .collectList()
                        .block())
                .stream()
                .reduce(0 , Integer::sum);

    }

    private String findShoppingCartQuantity(List<ShoppingCartItemDto> shoppingCartItemDtos, Long itemId){
        return shoppingCartItemDtos.stream()
                .filter(shoppingCartItemDto -> shoppingCartItemDto.getItemId().equals(itemId.toString()))
                .findFirst()
                .map(ShoppingCartItemDto::getQuantity)
                .orElse("0");
    }

    public List<OrderedItemDto> findAllOrderedItems(String userId) {
        return orderServiceClient.getOrderedItemsByUserRequest(userId);
    }
}
