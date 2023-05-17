package com.pyeontect.auth.controller;

import com.pyeontect.auth.dto.LoginRequestDto;
import com.pyeontect.auth.service.LoginService;
import com.pyeontect.auth.dto.SignResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/ptect")
    public ResponseEntity<SignResponseDto> signIn(@RequestBody LoginRequestDto requestDto) throws Exception {
        return new ResponseEntity<>(loginService.ptectLogin(requestDto), HttpStatus.OK);
    }

    /*
    private final LoginService loginService;

    @PostMapping("/ptect")
    public ResponseEntity<ResponseDto<Map<String, String>>> ptectLogin(@RequestBody LoginRequestDto loginRequestDto) {

        try {
            Map<String, String> tokenMap = loginService.ptectLogin(requestDto.get("phone"), requestDto.get("password"));
            return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.OK, "Log in successfully", tokenMap));
            //return ResponseEntity.ok().body("Log in successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }

        try {
            log.info("MAP 출력 이전");
            Map<String, String> tokenMap = loginService.ptectLogin(loginRequestDto);
            log.info("tokenMap 받아왔음");
            return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.OK, "Log in successfully", tokenMap));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }
 */
}
