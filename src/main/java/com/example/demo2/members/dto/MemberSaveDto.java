package com.example.demo2.members.dto;

import com.example.demo2.members.Member;
import com.example.demo2.members.Role;
import lombok.Getter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@Getter
public class MemberSaveDto {
    private String name;
    private String password;
    private String email;

    private Role role;
    private LocalDateTime regDate;

    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .name(name)
                .password(encodedPassword)
                .email(email)
                .role(role)
                .regDate(regDate)
                .build();
    }
}
