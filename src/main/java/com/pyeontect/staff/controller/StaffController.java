package com.pyeontect.staff.controller;

import com.pyeontect.common.ResponseDto;
import com.pyeontect.staff.dto.RequestResponseDto;
import com.pyeontect.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/owner/staff")
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/request")
    public ResponseEntity<ResponseDto<Object>> getList(@RequestParam("phone") String phone) {
        try {
            RequestResponseDto responseDto = staffService.getRequestList(phone);
            return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.OK.value()), "List lookup successfully.", responseDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.BAD_REQUEST.value()), e.getMessage()));
        }
    }

    // 수정필요
    @PostMapping("/request/{phone}/permit")
    private ResponseEntity<ResponseDto<Object>> permitClerk(@PathVariable("phone") String phone) {
        return null;
    }
}
