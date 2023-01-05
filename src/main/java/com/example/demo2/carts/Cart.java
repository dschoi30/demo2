package com.example.demo2.carts;

import com.example.demo2.members.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Cart {

    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    
}
