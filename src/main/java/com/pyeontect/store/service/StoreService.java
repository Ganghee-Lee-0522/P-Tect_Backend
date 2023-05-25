package com.pyeontect.store.service;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.member.domain.Role;
import com.pyeontect.store.domain.*;
import com.pyeontect.store.dto.StoreRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final EmploymentRepository employmentRepository;
    private final RequestRepository requestRepository;

    // 점포 등록
    // 만약 점주라면 바로 점포 등록 가능
    // 점원이라면 점포가 기등록 되어 있을 때만 등록 가능
    public boolean register(StoreRequestDto storeRequestDto) throws Exception {
        if (storeRequestDto.getStoreSite().isEmpty()) {
            throw new Exception("Invalid request.");
        }
        Member member = memberRepository.findByPhone(storeRequestDto.getPhone())
                .orElseThrow(() -> new Exception("Account not found."));
        
        // 만약 멤버가 점주이면
        if(member.getRole() == Role.OWNER) {
            // 점포 등록
            Store store = Store.builder()
                    .storeSite(storeRequestDto.getStoreSite())
                    .subscribe(false)
                    .build();

            storeRepository.save(store);

            // Employment에 근무 등록 -> 은 안해도 될 듯. 점원만 등록하기로.
            /*
            Employment employment = Employment.builder()
                    .store(store)
                    .member(member)
                    .build();

            employmentRepository.save(employment);
            */
        }
        
        // 만약 멤버가 점원이면
        if(member.getRole() == Role.CLERK) {
            // 점포가 등록되어 있는지 확인
            // 등록 X 시 Exception 발생
            Store store = storeRepository.findByStoreSite(storeRequestDto.getStoreSite())
                    .orElseThrow(() -> new Exception("Store not found."));

            // 점포가 등록되어 있으면 -> 근무자 등록 요청
            Request request = Request.builder()
                    .member(member)
                    .store(store)
                    .build();

            requestRepository.save(request);

            return false;
        }
        return true;
    }
}
