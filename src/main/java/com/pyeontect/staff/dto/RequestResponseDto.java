package com.pyeontect.staff.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class RequestResponseDto {
    List<RequestListFormat> requestList;

    @Builder
    public RequestResponseDto(List<RequestListFormat> requestList) {
        this.requestList = requestList;
    }
}
