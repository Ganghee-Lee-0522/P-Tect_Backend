package com.pyeontect.member.service;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.member.dto.ChangeRoleDto;
import com.pyeontect.member.dto.GetMemberInfoDto;
import com.pyeontect.member.dto.PatchRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean isPhoneDuplicate(String phone) {
        try {
            getMember(phone);
            // 이미 존재하는 회원의 경우 Exception이 반환되지 않음
            return true;
        } catch (Exception e) {
            // 존재하지 않는 회원의 경우 Exception이 반환됨
            return false;
        }
    }

    public GetMemberInfoDto getMember(String phone) throws Exception {
        Member member = memberRepository.findByPhone(phone)
                .orElseThrow(() -> new Exception("Account not found."));
        return new GetMemberInfoDto(member);
    }

    public String changeRole(ChangeRoleDto changeRoleDto) throws Exception {
        Member member = memberRepository.findByPhone(changeRoleDto.getPhone())
                .orElseThrow(() -> new Exception("Account not found."));
        log.info(member.getRoleKey());
        String newRole = member.updateRole(changeRoleDto.getRoleKey());
        log.info(newRole);

        return newRole;
    }

    public String changeName(PatchRequestDto patchRequestDto) throws Exception {
        Member member = memberRepository.findByPhone(patchRequestDto.getPhone())
                .orElseThrow(() -> new Exception("Account not found."));
        try {
            member.updateName(patchRequestDto.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return member.getName();
    }

    public String changePhone(PatchRequestDto patchRequestDto) throws Exception {
        Member member = memberRepository.findByPhone(patchRequestDto.getPhone())
                .orElseThrow(() -> new Exception("Account not found."));
        try {
            member.updatePhone(patchRequestDto.getPhone());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return member.getPhone();
    }

    public String changePassword(PatchRequestDto patchRequestDto) throws Exception {
        Member member = memberRepository.findByPhone(patchRequestDto.getPhone())
                .orElseThrow(() -> new Exception("Account not found."));
        try {
            member.updatePassword(passwordEncoder.encode(patchRequestDto.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return member.getPhone();
    }
}
