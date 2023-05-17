package com.pyeontect.member.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TABLE_MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, name = "member_name")
    private String name;

    @Column(unique = true, name = "member_phone")
    private String phone;

    @Column(nullable = false, name = "member_pw")
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name, String phone, Role role, String password) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.role = role;
    }

    /*
    public Member(String subject, String s, Collection<? extends GrantedAuthority> authorities) {
    }
    */

    public Member updateName(String name) {
        this.name = name;
        return this;
    }

    public Member updatePhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Member updatePassword(String password) {
        this.password = password;
        return this;
    }

    public String updateRole(String roleKey) throws Exception {
        if(roleKey.equals("CLERK")) {
            this.role = Role.CLERK;
            log.info("ROLE KEY가 CLERK이며, role이 바뀌었음");
        }
        else if(roleKey.equals("OWNER")){
            this.role = Role.OWNER;
            log.info("ROLE KEY가 OWNER이며, role이 바뀌었음");
        }
        return this.role.getTitle();
    }

    public String getRoleKey() {
        return this.role.getKey();
    }

}
