package com.pyeontect.auth.controller;
/*
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// 재작성 요망

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 로그인 URL 설정
        http.formLogin().loginPage("/auth/kakao").permitAll();

        // 로그아웃 URL 설정
        http.logout().logoutUrl("/logout").permitAll();

        // 인증되지 않은 사용자의 접근 제한
        http.authorizeRequests()
                .antMatchers("/auth/kakao/**").permitAll()
                .anyRequest().authenticated();

        // JWT 토큰 필터 설정
        http.addFilterBefore(new JwtAuthenticationFiter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        // 인증을 처리할 Provider 등록
        auth.authenticationProvider(new CustomAuthenticationProvider());
    }
}
 */
/*
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    public SecurityConfig(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    //서버가 실행되면 모든 HTTP 요청이 SecurityConfig로 들어옴
    //이 때 "/login/**", "/signup/**" 요청에 대해 모두 접근가능하게 함
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf(cross site request forgery): 사이트간 위조 요청
                // 인증된 사용자의 토큰을 취해 위조된 요청을 보냈을 경우 파악해 방지하는 것
                // rest api에서는 권한이 필요한 요청을 위해 인증 정보를 포함 시켜야 하나,
                // 서버에 인증 정보를 저장하지 않기 때문에 disable(JWT를 쿠키에 저장하지 않음)
                .csrf().disable() 
                // JWT를 사용하므로 세션 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Form Based Authentication 사용하지 않음
                .formLogin().disable()
                // HTTP Basic Authentication 사용하지 않음
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/login/**", "/signup/**").permitAll()
                .and()
                // UsernamePasswordAuthenticationFilter에 접근 전에
                // 직접 만든 JwtAuthenticationFilter 실행
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
*/

import com.pyeontect.auth.service.JwtAuthenticationFilter;
import com.pyeontect.auth.service.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final JwtProvider jwtProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // h2 db를 쓰기 위해 임시 작성
                .headers()
                .frameOptions()
                .sameOrigin()
                .and()
                //여기서 임시작성 끝.
                // phone, password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic().disable()
                // 쿠키 기반이 아닌 JWT 기반이므로 사용하지 않음
                // csrf(cross site request forgery): 사이트간 위조 요청
                // 인증된 사용자의 토큰을 취해 위조된 요청을 보냈을 경우 파악해 방지하는 것
                // rest api에서는 권한이 필요한 요청을 위해 인증 정보를 포함 시켜야 하나,
                // 서버에 인증 정보를 저장하지 않기 때문에 disable(JWT를 쿠키에 저장하지 않음)
                .csrf().disable()
                // CORS 설정
                .cors(c -> {
                            CorsConfigurationSource source = request -> {
                                // CORS 허용 패턴
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(
                                        List.of("*")
                                );
                                config.setAllowedMethods(
                                        List.of("*")
                                );
                                return config;
                            };
                            c.configurationSource(source);
                        }
                )
                // SpringSecurity 세션 정책 : 세션을 생성 및 사용하지 않음
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 조건 별로 요청 허용/제한 설정
                .authorizeRequests()
                // 회원가입과 로그인은 모두 승인
                .antMatchers("/signup/**", "/login/**", "/h2-console", "/h2-console/**").permitAll()
                //.antMatchers("/**", "/h2-console/**").permitAll()
                // /OWNER로 시작하는 요청은 OWNER 권한이 있는 유저에게만 허용
                .antMatchers("/owner/**").hasRole("OWNER")
                // /CLERK로 시작하는 요청은 CLERK 권한이 있는 유저 & OWNER 권한이 있는 유저에게만 허용
                .antMatchers("/clerk/**").hasAnyRole("CLERK", "OWNER")
                .antMatchers("/account/**").hasAnyRole("CLERK", "OWNER", "GUEST")
                .antMatchers("/store/**").hasAnyRole("CLERK", "OWNER")
                .anyRequest().denyAll()
                .and()
                // JWT 인증 필터 적용
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                // 에러 핸들링
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        // 권한 문제 발생 시, 이 부분 호출
                        response.setStatus(403);
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("text/html; charset=UTF-8");
                        response.getWriter().write("Unauthorized user.");
                    }
                })
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                        // 인증 문제 발생 시, 이 부분 호출
                        response.setStatus(401);
                        response.setCharacterEncoding("utf-8");
                        response.setContentType("text/html; charset=UTF-8");
                        response.getWriter().write("Unauthenticated user.");
                    }
                });

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // PasswordEncoder를 createDelegatingPasswordEncoder()로 설정하면
        //{noop} asdf!@#asdfvz!@#... 처럼
        // password의 앞에 Encoding 방식이 붙은채로 저장되어 암호화 방식을 지정하여 저장할 수 있음
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}