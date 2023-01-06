package com.example.demo2.carts.repository;

import com.example.demo2.carts.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
