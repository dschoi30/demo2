package com.example.demo2.members.dto;

import com.example.demo2.members.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
    private Long id;
    private String name;
    private String password;
    private String email;

    public MemberResponseDto(Member entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
    }
}
