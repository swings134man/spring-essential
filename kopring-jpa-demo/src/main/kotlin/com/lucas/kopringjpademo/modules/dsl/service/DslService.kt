package com.lucas.kopringjpademo.modules.dsl.service

import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.repository.DslRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DslService(
    private val dslRepository: DslRepository
) {

    @Transactional(readOnly = true)
    fun findByNameLike(name: String): List<DslEntity> {
        return dslRepository.findByNameLike(name)
    }

    @Transactional(readOnly = true)
    fun findByNameIn(names: List<String>): List<DslEntity> {
        return dslRepository.findByNameIn(names)
    }

}