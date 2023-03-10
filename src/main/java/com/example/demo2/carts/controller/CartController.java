package com.example.demo2.carts.controller;

import com.example.demo2.carts.dto.CartItemDto;
import com.example.demo2.carts.dto.CartListItemDto;
import com.example.demo2.carts.service.CartService;
import com.example.demo2.orders.dto.CartOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartItemDto cartItemDto, BindingResult bindingResult, Principal principal) {
        if(bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String userName = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, userName);
        } catch(Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping("/cart")
    public String cartList(Principal principal, Model model) {
        List<CartListItemDto> cartListItemDtos = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartListItemDtos);
        return "carts/cartList";
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity updateCartItem(@PathVariable("cartItemId") Long cartItemId, int count, Principal principal) {
        if(count <= 0) {
            return new ResponseEntity<String>("?????? 1??? ?????? ????????? ?????????.", HttpStatus.BAD_REQUEST);
        } else if(!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("?????? ????????? ????????????.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItem(cartItemId, count);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody ResponseEntity deleteCartItem(@PathVariable("cartItemId") Long cartItemId, Principal principal) {
        if(!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("?????? ????????? ????????????.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId);
        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity orderCartItem(@RequestBody CartOrderDto cartOrderDto, Principal principal) {

        List<CartOrderDto> cartOrderDtos = cartOrderDto.getCartOrderDtos();

        if(cartOrderDtos == null || cartOrderDtos.size() == 0) {
            return new ResponseEntity<String>("????????? ????????? ???????????? ???????????????.", HttpStatus.BAD_REQUEST);
        }

        for(CartOrderDto cartOrder : cartOrderDtos) {
            if(!cartService.validateCartItem(cartOrder.getCartItemId(), principal.getName())) {
                return new ResponseEntity<String>("?????? ????????? ????????????.", HttpStatus.FORBIDDEN);
            }
        }

        Long orderId = cartService.orderCartItem(cartOrderDtos, principal.getName());
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
}
