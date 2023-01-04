package com.example.demo2.members.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateDto {

    private String password;
    private String email;

    @Builder
    public MemberUpdateDto(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
