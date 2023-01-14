package com.example.demo2.carts.service;

import com.example.demo2.carts.Cart;
import com.example.demo2.carts.CartItem;
import com.example.demo2.carts.dto.CartItemDto;
import com.example.demo2.carts.dto.CartListItemDto;
import com.example.demo2.carts.repository.CartItemRepository;
import com.example.demo2.carts.repository.CartRepository;
import com.example.demo2.items.Item;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CartService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public Long addCart(CartItemDto cartItemDto, String userName) {
        Item item = itemRepository.findById(cartItemDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByName(userName);
        Cart cart = cartRepository.findByMemberId(member.getId());
        if(cart == null) {
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        CartItem savedCartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(), item.getId());

        if(savedCartItem != null) {
            savedCartItem.addCount(cartItemDto.getCount());
            return savedCartItem.getId();
        } else {
            CartItem cartItem = CartItem.createCartItem(cart, item, cartItemDto.getCount());
            cartItemRepository.save(cartItem);
            return cartItem.getId();
        }
    }

    public List<CartListItemDto> getCartList(String userName) {

        List<CartListItemDto> cartListItemDtos = new ArrayList<>();

        Member member = memberRepository.findByName(userName);
        Cart cart = cartRepository.findByMemberId(member.getId());

        if(cart == null) {
            return cartListItemDtos;
        }

        cartListItemDtos = cartItemRepository.findCartListItemDtos(cart.getId());

        return cartListItemDtos;
    }

    public boolean validateCartItem(Long cartItemId, String userName) {
        Member member = memberRepository.findByName(userName);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = cartItem.getCart().getMember();

        return StringUtils.equals(member.getName(), savedMember.getName());
    }

    @Transactional
    public void updateCartItem(Long cartItemId, int count) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItem.updateCount(count);
    }

    @Transactional
    public void deleteCartItem(Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);
        cartItemRepository.delete(cartItem);
    }
}
