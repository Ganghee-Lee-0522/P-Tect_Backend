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
@Table(name = "TABLE_REQUEST")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    //@Column(nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "store_id")
    //@Column(nullable = false)
    private Store store;

    @Column(name = "owner")
    private String owner;

    @Builder
    public Request(Member member, Store store) {
        this.member = member;
        this.store = store;
    }
}

