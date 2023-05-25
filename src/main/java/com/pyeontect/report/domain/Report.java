package com.pyeontect.report.domain;

import com.pyeontect.member.domain.Member;
import com.pyeontect.store.domain.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "TABLE_REPORT")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(nullable = false, name = "report_type")
    private String reportType;

    @Column(name = "report_img")
    private String reportImg;

    @ManyToOne
    @JoinColumn(name = "store_id")
    //@Column(name = "report_store")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "member_id")
    //@Column(name = "report_member")
    private Member member;

}
