package com.example.demo2.orders.repository;

import com.example.demo2.items.ItemImage;
import com.example.demo2.orders.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
