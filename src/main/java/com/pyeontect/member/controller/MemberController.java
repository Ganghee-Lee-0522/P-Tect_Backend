package com.pyeontect.member.controller;

import com.pyeontect.common.ResponseDto;
import com.pyeontect.member.dto.ChangeRoleDto;
import com.pyeontect.member.dto.PatchRequestDto;
import com.pyeontect.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<Object>> getUser(@RequestParam String phone) throws Exception {
        try {
            return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.OK, "User information returned successfully.", memberService.getMember(phone)));
            //return new ResponseEntity<>(memberService.getMember(phone), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<ResponseDto<Object>> setRole(@RequestBody ChangeRoleDto changeRoleDto) throws Exception {
        //return new ResponseEntity<>(memberService.changeRole(changeRoleDto), HttpStatus.OK);
        String newRole= "손님";
        log.info(newRole);
        try {
            newRole = memberService.changeRole(changeRoleDto);
            //return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.CREATED, "Change role successfully.", newRole));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.CREATED, "Change role successfully.", newRole));
    }

    @PatchMapping("/patch/name/{phone}")
    public ResponseEntity<ResponseDto<Object>> changeName(@RequestBody PatchRequestDto patchRequestDto, @PathVariable("phone") String phone) throws Exception {
        String newName= "";
        if(patchRequestDto.getName().isEmpty() || phone.isEmpty()) {
            throw new Exception("Missing required information.");
        }
        try {
            newName = memberService.changeName(patchRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.CREATED, "Change name successfully.", newName));
    }

    @PatchMapping("/patch/phone/{phone}")
    public ResponseEntity<ResponseDto<Object>> changePhone(@RequestBody PatchRequestDto patchRequestDto, @PathVariable("phone") String phone) throws Exception {
        String newPhone= "";
        if(patchRequestDto.getPhone().isEmpty() || phone.isEmpty()) {
            throw new Exception("Missing required information.");
        }
        try {
            newPhone = memberService.changePhone(patchRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.CREATED, "Change phone successfully.", newPhone));
    }

    @PatchMapping("/patch/password/{phone}")
    public ResponseEntity<ResponseDto<Object>> changePassword(@RequestBody PatchRequestDto patchRequestDto, @PathVariable("phone") String phone) throws Exception {
        String newPassword= "";
        if(patchRequestDto.getPassword().isEmpty() || phone.isEmpty()) {
            throw new Exception("Missing required information.");
        }
        try {
            newPassword = memberService.changePassword(patchRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.CREATED, "Change password successfully.", newPassword));
    }
}
