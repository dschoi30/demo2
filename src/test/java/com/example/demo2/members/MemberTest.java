package com.example.demo2.members;

import com.example.demo2.carts.Cart;
import com.example.demo2.members.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberTest {

    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
//    @WithMockUser(username = "tester", roles = "USER")
    public void auditing_test() {
        Member newMember = Member.builder()
                .name("member1")
                .password("1234")
                .email("test@test.com")
                .role(Role.USER)
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(newMember);

        em.flush();
        em.clear();

        Member member = memberRepository.findById(newMember.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("createdDate : " + member.getCreatedDate());
        System.out.println("modifiedDate : " + member.getModifiedDate());
    }
}