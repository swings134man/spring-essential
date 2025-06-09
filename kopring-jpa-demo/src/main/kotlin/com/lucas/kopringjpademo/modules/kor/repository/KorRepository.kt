package com.lucas.kopringjpademo.modules.kor.repository

import com.lucas.kopringjpademo.modules.kor.entity.KorEntity
import org.springframework.data.jpa.repository.JpaRepository

interface KorRepository: JpaRepository<KorEntity, Long> {
    fun findOneById(korId: Long): KorEntity?
}