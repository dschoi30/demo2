package com.example.demo2.items.repository;

import com.example.demo2.items.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long>, ItemImageRepositoryCustom {
    List<ItemImage> findByItemIdOrderByIdAsc(Long itemId);
}
