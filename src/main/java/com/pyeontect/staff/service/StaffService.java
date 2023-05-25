package com.pyeontect.staff.service;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.staff.dto.RequestListFormat;
import com.pyeontect.staff.dto.RequestResponseDto;
import com.pyeontect.store.domain.Request;
import com.pyeontect.store.domain.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StaffService {

    private final MemberRepository memberRepository;
    private final RequestRepository requestRepository;
    
    // 등록 요청한 점원 리스트 조회
    public RequestResponseDto getRequestList(String phone) {
        try {
            Optional<Member> member = memberRepository.findByPhone(phone);
            if (member.isEmpty()) {
                throw new Exception("Account not found.");
            }
            else {
                List<Request> requests = requestRepository.findByOwner(phone);
                List<RequestListFormat> requestList = new ArrayList<>();

                for(Request r : requests) {
                    String clerkName = r.getMember().getName();
                    String clerkPhone = r.getMember().getPhone();
                    String storeSite = r.getStore().getStoreSite();
                    RequestListFormat requestListFormat = new RequestListFormat(clerkName, clerkPhone, storeSite);
                    requestList.add(requestListFormat);
                }
                return new RequestResponseDto(requestList);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
