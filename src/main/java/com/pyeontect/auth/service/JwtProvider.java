package com.pyeontect.auth.service;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.Role;
import com.pyeontect.member.service.JpaUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

/*
* 클라이언트로부터 JWT를 받고,
* 서버의 비밀 값과 JWT 헤더, 페이로드를 alg에 넣어 서명값과 같은지 확인
* 같으면 유저에 인가
*/
@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKey;
    private Key key;

    private final long exp = 1000L * 60 * 60;

    private final JpaUserDetailsService userDetailsService;
    
    @PostConstruct
    protected void init() {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
    
    // access token 생성
    public String createAccessToken(String phone, Role role) {
        Claims claims = Jwts.claims().setSubject(phone);
        claims.put("role", role);
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp)) // 만료시간: 1시간 후
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /*
    // refresh token 생성
    public String createRefreshToken(Authentication auth) {
        String authorities = getAuthorities(auth);
        Date now = new Date();
        return Jwts.builder()
                .setIssuer("PyeonTect")
                // 안전하게 Authentication 객체에서 Principal 객체를 가져오고, Principal 객체에서 사용자 정보를 가져와 토큰을 생성하는 것이 좋음
                .setSubject(((Member) auth.getPrincipal()).getPhone())
                .claim("token_type", "refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + exp * 72)) // 만료시간: 72시간 후
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

     */

    // 권한 정보 획득
    // SpringSecurity 인증 과정에서 권한 확인을 위한 기능임
    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getPhone(accessToken));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에 담겨있는 유저 phone 획득
    private String getPhone(String accessToken) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody().getSubject();
    }

    // Authorization Header를 통한 인증
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // access token 검증
    public boolean validateAccessToken(String accessToken) {
        try {
            // Bearer 검증
            if(!accessToken.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                accessToken = accessToken.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken);
            // 만료 시 false 반환
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    /*
    public JwtProvider(@Value("${jwt.secret}") String secretKey) {
        key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        //byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
        //this.key = Keys.hmacShaKeyFor(secretByteKey);
    }
    */

    /*
     * 토큰 생성 함수
     * 복호화에 사용할 알고리즘은 HS256
     * 키는 256비트 이상이 되어야 함
     */
    /*
    public Map<String, String> createToken(Authentication auth) {

        // access token 생성
        String accessToken = createAccessToken(auth);

        // refresh token 생성
        String refreshToken = createRefreshToken(auth);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);

        return tokenMap;
    }
    */

    /*
     * 토큰 복호화 함수
     */
    /*
    public Authentication decryptToken(String accessToken) {
        Claims claims = parseAccessTokenClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("Token without permission information");
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
        //아래 코드대로 실행하면 순환 참조 오류 생김..
        //Claims claims = parseAccessTokenClaims(accessToken);
        //UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        //return new UsernamePasswordAuthenticationToken(userDetails, accessToken, userDetails.getAuthorities());

    }
    */

    /*
     * access token 검증 함수
     */
    /*
    public boolean validateAccessToken(String accessToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken);
            return true;
        } catch (SignatureException e) {
            // 서명 오류 발생
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            // 토큰 만료됨
            log.info("Expired JWT Token", e);
        } catch (Exception e) {
            // 그 외 다른 예외 발생
            log.info("Unsupported JWT Token", e);
        }
        return false;
    }
    */

    /*
    public boolean validateAccessToken(String accessToken) {
        try {
            // JWT 토큰 파서 생성, 키 설정, 유효성 검사, body 반환
            Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
            //Jwts.parserBuilder().setSignKey(key).build().parseClaimsJws(token);
            return true;

            // 토큰이 유효하지 않으면 catch 블록으로 처리
            // 예외 발생 시 로그에 기록됨
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }
    */

    /*
     * refresh token 검증 함수
     */
    /*
    public boolean validateRefreshToken(String refreshToken) {
        try {
            // JWT 토큰 파서 생성, 키 설정, 유효성 검사, body 반환
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(refreshToken);
            // 유효한 토큰인지 검사
            if (!claimsJws.getBody().get("token_type").equals("refresh")) {
                return false;
            }
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Refresh Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Refresh Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Refresh Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT Refresh Token claims string is empty", e);
        }
        return false;
    }
    */

    /*
     * access token에서 Claims 정보를 추출하는 함수
     */
    /*
    private Claims parseAccessTokenClaims(String accessToken) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody();
            //return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();

            // catch 블록으로 처리
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new RuntimeException("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
            throw new RuntimeException("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new RuntimeException("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
            throw new RuntimeException("JWT claims string is empty", e);
        }
    }
    */

    /*
     * refresh token에서 Claims 정보를 추출하는 함수
     */
    /*
    private Claims parseRefreshTokenClaims(String refreshToken) {
        try {
            return Jwts.parser().setSigningKey(key).parseClaimsJws(refreshToken).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Refresh Token", e);
            return e.getClaims();
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException |
                 IllegalArgumentException e) {
            log.info("Invalid JWT Refresh Token", e);
            throw new JwtException("Invalid JWT Refresh Token");
        }
    }
    */

    /*
     * access token에서 username 정보를 추출하는 함수
     *  궁금한점.. => username 없는데 어케 추출함?
     */
    /*
    public String getUsernameFromToken(String token) {
        Claims claims = parseAccessTokenClaims(token);
        return claims.getSubject();
    }
    */

    /*
     * access token에서 authorities 정보를 추출하는 함수
     */
    /*
    public Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token) {
        Claims claims = parseAccessTokenClaims(token);
        return Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
    */

    /*
     * refresh token에서 username 정보를 추출하는 함수
     * 궁금한점.. => username 없는데 어케 추출함?
     */
    /*
    public String getUsernameFromRefreshToken(String refreshToken) {
        Claims claims = parseRefreshTokenClaims(refreshToken);
        return claims.getSubject();
    }

    private String createAccessToken(Authentication auth) {
        String authorities = getAuthorities(auth);

        // Access Token 생성
        String accessToken = Jwts.builder()
                // 안전하게 Authentication 객체에서 Principal 객체를 가져오고, Principal 객체에서 사용자 정보를 가져와 토큰을 생성하는 것이 좋음
                .setSubject(((Member) auth.getPrincipal()).getPhone())
                .claim("auth", authorities)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30)) // 30일 만료
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return accessToken;
    }

    private String createRefreshToken(Authentication auth) {
        //String authorities = getAuthorities(auth);

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setIssuer("PyeonTect")
                // 안전하게 Authentication 객체에서 Principal 객체를 가져오고, Principal 객체에서 사용자 정보를 가져와 토큰을 생성하는 것이 좋음
                .setSubject(((Member) auth.getPrincipal()).getPhone())
                .claim("token_type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 72))) // 72일 만료
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return refreshToken;
    }


     */
    /*
    private String getAuthorities(Authentication auth) {
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        return authorities;
    }
     */

    /*
     * access token 갱신 함수
     */
    /*
    public Map<String, String> refreshToken(String refreshToken) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(refreshToken).getBody();

        // access token 생성
        String accessToken = Jwts.builder()
                .setSubject(claims.get("phone").toString())
                .claim("auth", claims.get("auth"))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30)) // 30일 만료
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // refresh token 생성
        String newRefreshToken = Jwts.builder()
                .setIssuer("PyeonTect")
                .setSubject(claims.get("phone").toString())
                .claim("token_type", "refresh")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 72))) // 72일 만료
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", newRefreshToken);

        return tokenMap;
    }
    */
}