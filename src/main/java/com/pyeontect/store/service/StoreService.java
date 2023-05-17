package com.pyeontect.store.service;

import com.pyeontect.member.domain.Member;
import com.pyeontect.member.domain.MemberRepository;
import com.pyeontect.member.dto.GetMemberInfoDto;
import com.pyeontect.store.domain.Store;
import com.pyeontect.store.domain.StoreRepository;
import com.pyeontect.store.dto.StoreRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    public boolean register(StoreRequestDto storeRequestDto) throws Exception {
        if (storeRequestDto.getStoreSite().isEmpty()) {
            throw new Exception("Invalid request.");
        }
        Member member = memberRepository.findByPhone(storeRequestDto.getPhone())
                .orElseThrow(() -> new Exception("Account not found."));
        try {
            Store store = Store.builder()
                    .storeSite(storeRequestDto.getStoreSite())
                    .subscribe(false)
                    .build();

            storeRepository.save(store);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
