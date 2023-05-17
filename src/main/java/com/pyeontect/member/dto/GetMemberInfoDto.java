package com.pyeontect.member.dto;

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
public class GetMemberInfoDto {
    private String phone;
    private String name;
    private Role role;

    public GetMemberInfoDto(Member member) {
        this.phone = member.getPhone();
        this.name = member.getName();
        this.role = member.getRole();
    }
}
