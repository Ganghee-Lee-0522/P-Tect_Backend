package com.pyeontect.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {
    Optional<Request> findById(Long id);
    List<Request> findByOwner(String owner);
}
