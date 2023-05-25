package com.pyeontect.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {
    Optional<Store> findById(Long id);
    Optional<Store> findByStoreSite(String storeSite);
}
