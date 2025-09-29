package com.lucas.bootbasic.modules.events.exceptions;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ErrorRepository extends JpaRepository<ErrorEntity, Long> {
}
