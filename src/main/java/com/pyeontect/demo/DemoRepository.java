package com.pyeontect.demo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DemoRepository extends JpaRepository<Demo, Long> {
    Optional<Demo> findById(Long id);
}
