package com.pyeontect.auth.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class VerifyRequestDto {

    @NotBlank(message = "There is no phone")
    private String phone;
}
