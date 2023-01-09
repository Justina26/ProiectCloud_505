package com.unibuc.ordersmicroservice.domain.mapper;

import com.unibuc.ordersmicroservice.domain.dto.OrderedItemDto;
import com.unibuc.ordersmicroservice.domain.dto.OrderedItemsRequest;
import com.unibuc.ordersmicroservice.domain.entity.OrderedItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderedItemsMapper {

    OrderedItemDto toDto(OrderedItem orderedItem);
    OrderedItem toEntity(OrderedItemsRequest orderedItemsRequest);


}
