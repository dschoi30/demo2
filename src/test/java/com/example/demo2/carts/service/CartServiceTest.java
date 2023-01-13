package com.example.demo2.carts.service;

import com.example.demo2.carts.CartItem;
import com.example.demo2.carts.dto.CartItemDto;
import com.example.demo2.carts.repository.CartItemRepository;
import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.Role;
import com.example.demo2.members.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class CartServiceTest {

    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CartService cartService;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Item saveItem() {
        return Item.builder()
                .itemName("item1")
                .description("nice")
                .price(10000)
                .stockQuantity(100)
                .itemSaleStatus(ItemSaleStatus.SELL)
                .build();
    }

    public Member saveMember() {
        return Member.builder()
                .name("tester")
                .password("1234")
                .email("test@test.com")
                .role(Role.USER).build();
    }

    @Test
    public void addCart() {
        Item item = itemRepository.save(saveItem());
        Member member = memberRepository.save(saveMember());

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(1);
        cartItemDto.setItemId(item.getId());

        Long savedCartItemId = cartService.addCart(cartItemDto, member.getName());

        CartItem cartItem = cartItemRepository.findById(savedCartItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(),cartItem.getCount());
    }
}