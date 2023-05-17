package com.pyeontect.auth.dto;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignResponseDto {
    private String phone;
    private String name;
    private Role role;
    private String token;

    public SignResponseDto(Member member) {
        this.phone = member.getPhone();
        this.name = member.getName();
        this.role = member.getRole();
    }
}
