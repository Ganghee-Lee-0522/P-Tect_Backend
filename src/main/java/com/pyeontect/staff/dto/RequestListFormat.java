package com.pyeontect.staff.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestListFormat {
    private String clerkName;
    private String clerkPhone;
    private String storeSite;

    @Builder
    public RequestListFormat(String clerkName, String clerkPhone, String storeSite) {
        this.clerkName = clerkName;
        this.clerkPhone = clerkPhone;
        this.storeSite = storeSite;
    }
}
