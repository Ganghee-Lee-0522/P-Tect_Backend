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

    @ManyToOne
    @JoinColumn(name = "store_id")
    //@Column(nullable = false)
    private Store store;

    @ManyToOne
    @JoinColumn(name = "member_id")
    //@Column(nullable = false)
    private Member member;

    @Column(nullable = false, name = "report_type")
    private String reportType;

    @Column(name = "report_img")
    private String reportImg;

}
