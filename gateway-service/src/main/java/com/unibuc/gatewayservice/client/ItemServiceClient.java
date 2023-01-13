package com.unibuc.gatewayservice.client;

import com.unibuc.gatewayservice.config.properties.ItemServiceProperties;
import com.unibuc.gatewayservice.domain.items.AddCategoryRequest;
import com.unibuc.gatewayservice.domain.items.AddItemRequest;
import com.unibuc.gatewayservice.domain.items.CategoryDto;
import com.unibuc.gatewayservice.domain.items.ItemDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class ItemServiceClient {

    private final WebClient itemServiceWebClient;
    private final ItemServiceProperties itemServiceProperties;

    public List<ItemDto> getAllItemsRequest() {
        return itemServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(itemServiceProperties.getItemsEndpoint()).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ItemDto>>() {
                })
                .block();

    }

    public Mono<ItemDto> getSingleItemByIdRequest(String itemId) {
        return itemServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(itemServiceProperties.getSingleItemEndpoint()).build(itemId))
                .retrieve()
                .bodyToMono(ItemDto.class);
    }


    public List<CategoryDto> getAllCategoriesRequest() {
        return itemServiceWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(itemServiceProperties.getCategoriesEndpoint()).build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CategoryDto>>() {
                })
                .block();

    }

    public ResponseEntity<?> createItemRequest(AddItemRequest addItemRequest) {
        return itemServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(itemServiceProperties.getItemsEndpoint()).build())
                .body(Mono.just(addItemRequest), AddItemRequest.class)
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();
    }

    public ResponseEntity<?> createCategoryRequest(AddCategoryRequest addCategoryRequest) {
        return itemServiceWebClient.post()
                .uri(uriBuilder -> uriBuilder.path(itemServiceProperties.getCategoriesEndpoint()).build())
                .body(Mono.just(addCategoryRequest), AddCategoryRequest.class)
                .retrieve()
                .bodyToMono(ResponseEntity.class)
                .block();
    }

    public Mono<ItemDto> updateItemQuantity(String itemId, String quantity){
        return itemServiceWebClient.put()
                .uri(uriBuilder -> uriBuilder.path(itemServiceProperties.getUpdateItemEndpoint())
                        .queryParam("itemId", itemId)
                        .queryParam("quantity", quantity).build())
                .retrieve()
                .bodyToMono(ItemDto.class);
    }


}
