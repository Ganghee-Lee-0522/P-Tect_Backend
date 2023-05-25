package com.pyeontect.store.controller;

import com.pyeontect.common.ResponseDto;
import com.pyeontect.store.dto.StoreRequestDto;
import com.pyeontect.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    // 점포 등록
    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Object>> registerStore(@RequestBody StoreRequestDto storeRequestDto) throws Exception {
        try {
            if (storeService.register(storeRequestDto) == false) {
                return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.OK.value()), "Registration request completed."));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.EXPECTATION_FAILED.value()), e.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.CREATED.value()), "Register store successfully."));
    }
}
