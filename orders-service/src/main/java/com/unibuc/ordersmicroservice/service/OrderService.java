package com.unibuc.ordersmicroservice.service;

import com.unibuc.ordersmicroservice.domain.dto.CreateOrderRequest;
import com.unibuc.ordersmicroservice.domain.dto.OrderDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemsRequest;
import com.unibuc.ordersmicroservice.domain.entity.Order;
import com.unibuc.ordersmicroservice.domain.entity.OrderedItem;
import com.unibuc.ordersmicroservice.domain.mapper.OrderedItemsMapper;
import com.unibuc.ordersmicroservice.domain.mapper.OrdersMapper;
import com.unibuc.ordersmicroservice.repository.OrderRepository;
import com.unibuc.ordersmicroservice.repository.OrderedItemsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderedItemsRepository orderedItemsRepository;
    private final OrdersMapper ordersMapper;
    private final OrderedItemsMapper orderedItemsMapper;

    public OrderDto createOrder(CreateOrderRequest createOrderRequest) {

        var order = ordersMapper.toEntity(createOrderRequest);

        setOrderForOrderedItems(order);

        return ordersMapper.toDto(orderRepository.save(order));
    }

    public List<OrderedItemDto> findAllOrderedItemsByUser(String userId){
        return orderedItemsRepository.findAllByUserId(userId)
                .stream()
                .map(orderedItemsMapper::toDto)
                .collect(Collectors.toList());
    }

    private void setOrderForOrderedItems(Order order) {
        order.getOrderedItems().forEach(orderedItem -> orderedItem.setOrder(order));
    }


}
