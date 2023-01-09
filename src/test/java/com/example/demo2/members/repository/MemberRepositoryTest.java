package com.example.demo2.members.repository;

import com.example.demo2.members.Member;
import com.example.demo2.members.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    public void create_test() {
        Member savedMember = memberRepository.save(Member.builder()
                .name("member1")
                .password("1234")
                .email("test@test.com")
                .role(Role.USER)
                .build());

        Member findMember = memberRepository.findAll().get(0);
        Assertions.assertThat(findMember.getName()).isEqualTo("member1");
    }
}