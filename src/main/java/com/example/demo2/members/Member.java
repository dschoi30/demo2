package com.example.demo2.members;

import com.example.demo2.carts.Cart;
import com.example.demo2.common.BaseTimeEntity;
import com.example.demo2.members.dto.MemberSaveDto;
import com.example.demo2.orders.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private Cart cart;

    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Order> orders;

    public static Member createMember(MemberSaveDto memberSaveDto, String encodedPassword) {
        return Member.builder()
                .name(memberSaveDto.getName())
                .password(encodedPassword)
                .email(memberSaveDto.getEmail())
                .role(Role.USER)
                .orders(new ArrayList<>())
                .build();
    }

    public Member update(String password, String email) {
        this.password = password;
        this.email = email;
        return this;
    }
}
