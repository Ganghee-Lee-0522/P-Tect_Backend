package com.pyeontect.store.controller;

import com.pyeontect.common.ResponseDto;
import com.pyeontect.store.dto.StoreRequestDto;
import com.pyeontect.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/store")
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Object>> registerStore(@RequestBody StoreRequestDto storeRequestDto) throws Exception {
        try {
            storeService.register(storeRequestDto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.EXPECTATION_FAILED.value()), e.getMessage()));
        }
        return ResponseEntity.ok().body(ResponseDto.response(HttpStatus.valueOf(HttpStatus.OK.value()), "Register store successfully."));
    }
}
