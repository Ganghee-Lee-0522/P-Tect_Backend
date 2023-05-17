package com.pyeontect.auth.service;

/*
import com.pyeontect.auth.service.JwtProvider;
import com.pyeontect.auth.domain.JwtToken;
import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.auth.dto.SignRequestDto;
import com.pyeontect.auth.dto.SignResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public SignResponseDto ptectLogin(SignRequestDto request) throws Exception {
        Member member = memberRepository.findByPhone(request.getPhone()).orElseThrow(() ->
                new BadCredentialsException("Invalid account information."));

        if(!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BadCredentialsException("Invalid account information.");
        }

        JwtToken jwtToken;
        jwtProvider.createAccessToken(member.getPhone(), member.getRole());
    jwtProvider.createRefreshToken()

        return SignResponseDto.builder()
                .phone(member.getPhone())
                .name(member.getName())
                .role(member.getRole())
                .token()
    }
}
*/
import com.pyeontect.auth.dto.SignRequestDto;
import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.member.domain.Role;
import com.pyeontect.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public boolean register(SignRequestDto request) throws Exception {

        if(memberService.isPhoneDuplicate(request.getPhone())) {
            throw new Exception("Already Exist.");
        }
        try {
            Member member = Member.builder()
                    .phone(request.getPhone())
                    .name(request.getName())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.GUEST) // 첫 가입 시에는 자동 GUEST 권한 부여
                    .build();

            memberRepository.save(member);
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new Exception("Invalid request.");
        }

        return true;
    }

    /*
    private final AuthenticationManagerBuilder authManagerBuilder;
    private final JwtProvider jwtProvider;

    public Map<String, String> ptectLogin(LoginRequestDto loginRequestDto) {
        log.info("LoginService의 ptectLogin 함수로 넘어옴");
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequestDto.getPhone(), loginRequestDto.getPassword());
        log.info("authToken 생성됨");
        // 검증
        Authentication auth = authManagerBuilder.getObject().authenticate(authToken);
        log.info("auth 생성됨");


        // 검증된 인증 정보로 JWT 토큰 생성
        Map<String, String> tokenMap = jwtProvider.createToken(auth);
        log.info("tokenMap 생성됨");

        return tokenMap;
    }

 */

    /*
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;


    public boolean verifyPhoneNum(VerifyRequestDto requestDto) {
        // 중복된 회원이 존재하면 true, 부재하면 false 반환
        return isPhoneNumExist(requestDto.getPhone());
    }

    public void signUp(SignUpRequestDto requestDto) throws Exception {
        try {
            boolean isDuplicated = isPhoneNumExist(requestDto.getPhone());

            if (isDuplicated) {
                throw new IllegalArgumentException("User already exists");
            }

            String encPwd = encoder.encode(requestDto.getPassword());
            Member member = memberRepository.save(requestDto.toEntity(encPwd));
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public boolean isPhoneNumExist(String phone) {
        //Optional<Member> member = memberRepository.findByPhone(phone);

        return false;

        // 중복된 회원이 존재하면 true, 부재하면 false 반환
        //return member != null;
    }
     */
}
