package com.pyeontect.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SignRequestDto {

    @NotBlank(message = "There is no name")
    private String name;

    @NotBlank(message = "There is no phone")
    // 여기에 전화번호 검증하는 어노테이션이나 함수도 있으면 좋을 듯
    private String phone;

    @NotBlank(message = "There is no password")
    private String password;
}
