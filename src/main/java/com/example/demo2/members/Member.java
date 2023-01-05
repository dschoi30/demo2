package com.example.demo2.members;

import com.example.demo2.carts.Cart;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String name;
    private String password;
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime regDate;

    @OneToOne(mappedBy = "member")
    private Cart cart;

    @Builder
    public Member(String name, String password, String email, Role role, LocalDateTime regDate) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.regDate = regDate;
    }

    public Member update(String password, String email) {
        this.password = password;
        this.email = email;
        return this;
    }
}
