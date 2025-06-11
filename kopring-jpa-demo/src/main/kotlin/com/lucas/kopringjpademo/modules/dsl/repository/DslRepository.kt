package com.lucas.kopringjpademo.modules.dsl.repository

import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DslRepository: JpaRepository<DslEntity, Long>, DslCustomRepository {
}