package com.pyeontect.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PatchRequestDto {
    private String phone;
    private String name;
    private String password;
}
