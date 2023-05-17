package com.pyeontect.member.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    OWNER("ROLE_OWNER", "점주"),
    CLERK("ROLE_CLERK", "점원");

    private final String key;
    private final String title;
}
