package com.unibuc.ordersmicroservice.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;

    private Integer price;

    @OneToMany(mappedBy="order", cascade = CascadeType.PERSIST)
    private List<OrderedItem> orderedItems;
}
