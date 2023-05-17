package com.pyeontect.auth.dto;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.Role;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class LoginRequestDto {

    @NotBlank(message = "There is no phone")
    private String phone;

    @NotBlank(message = "There is no password")
    private String password;
}
