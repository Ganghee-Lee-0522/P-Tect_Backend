package com.pyeontect.store.domain;

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
@Table(name = "TABLE_STORE")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(nullable = false, name = "store_site")
    private String storeSite;

    @Column(nullable = false, name = "store_subscribe")
    private Boolean subscribe;

    @Builder
    public Store(String storeSite, Boolean subscribe) {
        this.storeSite = storeSite;
        this.subscribe = subscribe;
    }
}
