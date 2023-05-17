package com.pyeontect.store.domain;

import com.pyeontect.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TABLE_EMPLOYMENT")
public class Employment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "store_id")
    //@Column(nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "member_id")
    //@Column(nullable = false)
    private Member member;
}
