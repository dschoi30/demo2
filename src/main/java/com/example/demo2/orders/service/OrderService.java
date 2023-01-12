package com.example.demo2.orders.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemImage;
import com.example.demo2.items.repository.ItemImageRepository;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.orders.Order;
import com.example.demo2.orders.OrderItem;
import com.example.demo2.orders.dto.OrderDto;
import com.example.demo2.orders.dto.OrderHistoryDto;
import com.example.demo2.orders.dto.OrderItemDto;
import com.example.demo2.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Long order(OrderDto orderDto, String userName) {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByName(userName);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());

        Order order = Order.createOrder(member, orderItems);
        order.addOrderItem(orderItem);
        orderRepository.save(order);

        return order.getId();
    }

    public Page<OrderHistoryDto> getOrderList(String userName, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(userName, pageable);
        Long totalCount = orderRepository.countOrder(userName);

        List<OrderHistoryDto> orderHistoryDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistoryDto orderHistoryDto = new OrderHistoryDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImage itemImage = itemImageRepository.findByItemIdAndIsRepImage(orderItem.getItem().getId(), true);
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImage.getImageUrl());
                orderHistoryDto.addOrderItemDto(orderItemDto);
            }
            orderHistoryDtos.add(orderHistoryDto);
        }
        return new PageImpl<OrderHistoryDto>(orderHistoryDtos, pageable, totalCount);
    }

    public boolean validateOrder(Long orderId, String userName) {
        Member member = memberRepository.findByName(userName);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        return StringUtils.equals(member, savedMember);
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }
}
