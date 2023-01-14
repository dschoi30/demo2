package com.example.demo2.carts.repository;

import com.example.demo2.carts.CartItem;
import com.example.demo2.carts.dto.CartListItemDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Query(
            "select new com.example.demo2.carts.dto.CartListItemDto(" +
                "ci.id, i.itemName, i.price, ci.count, im.imageUrl) " +
            "from CartItem ci, ItemImage im " +
            "join ci.item i " +
            "where ci.cart.id = :cartId " +
            "and im.item.id = ci.item.id " +
            "and im.isRepImage = true " +
            "order by ci.cart.modifiedDate desc"
    )
    List<CartListItemDto> findCartListItemDtos(@Param("cartId") Long cartId);
}
