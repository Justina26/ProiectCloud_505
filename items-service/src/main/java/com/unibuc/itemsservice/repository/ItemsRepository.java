package com.unibuc.itemsservice.repository;

import com.unibuc.itemsservice.domain.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Long> {
}
