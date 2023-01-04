package com.example.demo2.members.controller;

import com.example.demo2.members.Role;
import com.example.demo2.members.dto.MemberResponseDto;
import com.example.demo2.members.dto.MemberSaveDto;
import com.example.demo2.members.dto.MemberUpdateDto;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.members.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    @ModelAttribute("roles")
    public Role[] role() {
        return Role.values();
    }

    @GetMapping("/new")
    public String singUp(Model model) {
        model.addAttribute("member", new MemberSaveDto());
        return "members/memberCreationForm";
    }

    @GetMapping("/login")
    public String loginMember() {
        return "members/memberLogin";
    }

    @GetMapping("/{id}")
    public String memberInfo(Model model, @PathVariable Long id) {
        MemberResponseDto dto = memberService.findById(id);
        model.addAttribute("member", dto);
        return "members/memberInfo";
    }

    @GetMapping("/{id}/update")
    public String updateMember(Model model, @PathVariable Long id) {
        MemberResponseDto dto = memberService.findById(id);
        model.addAttribute("member", dto);
        return "members/memberUpdateForm";
    }
}
