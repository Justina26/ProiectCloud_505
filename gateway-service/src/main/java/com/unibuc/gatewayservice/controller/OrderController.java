package com.unibuc.gatewayservice.controller;



import com.unibuc.gatewayservice.domain.order.CreateOrderRequest;
import com.unibuc.gatewayservice.domain.order.OrderDto;
import com.unibuc.gatewayservice.domain.order.OrderedItemDto;
import com.unibuc.gatewayservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderDto createOrder(@RequestParam String userId){
        log.info("Creating order for userID: {}", userId);
        return orderService.createOrder(userId);
    }

    @GetMapping("/ordered-items")
    public List<OrderedItemDto> getOrderedItemsByUser(@RequestParam String userId){
        log.info("Attempting to get all ordered items for userID: {}", userId);
        return orderService.findAllOrderedItems(userId);
    }


}
