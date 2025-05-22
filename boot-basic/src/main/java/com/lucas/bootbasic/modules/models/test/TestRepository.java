package com.lucas.bootbasic.modules.models.test;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
    Optional<TestEntity> findByValue(String value);
}
