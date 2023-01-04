package com.example.demo2.api.members;

import com.example.demo2.members.dto.MemberSaveDto;
import com.example.demo2.members.dto.MemberUpdateDto;
import com.example.demo2.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public Long createMember(@RequestBody MemberSaveDto memberSaveDto) {
        return memberService.save(memberSaveDto);
    }

    @PutMapping("/api/v1/members/{id}")
    public Long updateMember(@PathVariable Long id, @RequestBody MemberUpdateDto memberUpdateDto) {
        return memberService.update(id, memberUpdateDto);
    }

    @DeleteMapping("/api/v1/members/{id}")
    public Long deleteMember(@PathVariable Long id) {
        memberService.delete(id);
        return id;
    }
}