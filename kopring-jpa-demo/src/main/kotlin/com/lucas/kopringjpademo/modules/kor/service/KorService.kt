package com.lucas.kopringjpademo.modules.kor.service

import com.lucas.kopringjpademo.modules.kor.entity.KorEntity
import com.lucas.kopringjpademo.modules.kor.repository.KorCommonRepository
import com.lucas.kopringjpademo.modules.kor.repository.KorRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class KorService(
    private val korRepository: KorRepository,
    private val korCommonRepository: KorCommonRepository,
) {

    @Transactional
    fun save(korEntity: KorEntity): KorEntity {
        return if(korEntity.id == null) createEntity(korEntity)
        else updateEntity(korEntity)
    }

    private fun createEntity(korEntity: KorEntity): KorEntity {
        return korRepository.save(korEntity)
    }

    private fun updateEntity(korEntity: KorEntity): KorEntity {
        val result = korRepository.findOneById(korEntity.id!!)
            ?: throw IllegalArgumentException("Kor with id ${korEntity.id} not found")

        result.apply {
            result.name = korEntity.name
            result.age = korEntity.age
            result.isActive = korEntity.isActive
        }

        return korRepository.save(result)
    }

    @Transactional(readOnly = true)
    fun findAllKor(): List<KorEntity>{
        return korRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun findById(id: Long): KorEntity? {
        return korRepository.findById(id).orElseThrow(
            { IllegalArgumentException("Kor with id $id not found") }
        )
    }

    // ---------------------------------------------- Common Repository ----------------------------------------------
    // Common Repo's QueryDSL
    @Transactional(readOnly = true)
    fun findByAgeGreaterThan(age: Int): List<KorEntity> {
        return korCommonRepository.findByAgeGreaterThan(age)
    }

    // Common Repo's JpaRepository
    @Transactional(readOnly = true)
    fun findByIdKor(id: Long): KorEntity? {
        return korCommonRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Kor with id $id not found") }
    }
}