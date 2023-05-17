package com.pyeontect.auth.service;

import com.pyeontect.auth.dto.LoginRequestDto;
import com.pyeontect.auth.dto.SignResponseDto;
import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignResponseDto ptectLogin(LoginRequestDto requestDto) throws Exception {
        Member member = memberRepository.findByPhone(requestDto.getPhone()).orElseThrow(() ->
                new BadCredentialsException("Invalid account information."));

        if (!passwordEncoder.matches(requestDto.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("Invalid account information.");
        }

        return SignResponseDto.builder()
                .phone(member.getPhone())
                .name(member.getName())
                .role(member.getRole())
                .token(jwtProvider.createAccessToken(member.getPhone(), member.getRole()))
                .build();
    }
}