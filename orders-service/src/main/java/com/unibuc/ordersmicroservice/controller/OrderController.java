package com.unibuc.ordersmicroservice.controller;

import com.unibuc.ordersmicroservice.domain.dto.CreateOrderRequest;
import com.unibuc.ordersmicroservice.domain.dto.OrderDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemDto;
import com.unibuc.ordersmicroservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestBody CreateOrderRequest createOrderRequest){
        log.info("Creating order for userID: {}", createOrderRequest.getUserId());
        return orderService.createOrder(createOrderRequest);
    }
    @GetMapping("/ordered-items")
    public List<OrderedItemDto> getOrderedItemsByUser(@RequestParam(name = "userId") String userId){
        log.info("Attempting to get all ordered items for userID: {}", userId);
        return orderService.findAllOrderedItemsByUser(userId);
    }
}
