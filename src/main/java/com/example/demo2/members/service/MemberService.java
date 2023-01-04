package com.example.demo2.members.service;

import com.example.demo2.members.Member;
import com.example.demo2.members.dto.MemberResponseDto;
import com.example.demo2.members.dto.MemberSaveDto;
import com.example.demo2.members.dto.MemberUpdateDto;
import com.example.demo2.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(MemberSaveDto memberSaveDto) {
        Member member = memberSaveDto.toEntity(passwordEncoder.encode(memberSaveDto.getPassword()));
        validateDuplicatedMember(member);
        return memberRepository.save(member).getId();
    }

    public void validateDuplicatedMember(Member member) {
        Member byName = memberRepository.findByName(member.getName());
        if(byName != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Member member = memberRepository.findByName(name);

        if(member == null){
            throw new UsernameNotFoundException(name);
        }

        return User.builder()
                .username(name)
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }

    public MemberResponseDto findById(Long id) {
        Member entity = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        return new MemberResponseDto(entity);
    }

    @Transactional
    public Long update(Long id, MemberUpdateDto memberUpdateDto) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        member.update(passwordEncoder.encode(memberUpdateDto.getPassword()), memberUpdateDto.getEmail());
        return member.getId();
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        memberRepository.delete(member);
    }
}
