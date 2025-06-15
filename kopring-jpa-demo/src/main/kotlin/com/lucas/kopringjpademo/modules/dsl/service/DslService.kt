package com.lucas.kopringjpademo.modules.dsl.service

import com.lucas.kopringjpademo.common.PageResponse
import com.lucas.kopringjpademo.common.toPageResponse
import com.lucas.kopringjpademo.modules.dsl.dto.DslResponseDTO
import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import com.lucas.kopringjpademo.modules.dsl.repository.DslRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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

    // DTO Response
    @Transactional(readOnly = true)
    fun findByAddressLike(address: String): List<DslResponseDTO> {
        return dslRepository.findByAddressLike(address)
    }


    // 공통 Page DTO 를 사용하지 않은 Paging Function
    @Transactional(readOnly = true)
    fun findByNamePaging(name: String, pageable: Pageable): Page<DslEntity> {
        return dslRepository.findByNamePaging(name, pageable)
    }

    // 공통 Page DTO 를 사용하는 Paging Function
    @Transactional(readOnly = true)
    fun findByNamePagingToDTO(name: String, pageable: Pageable): PageResponse<DslEntity> {
        return dslRepository.findByNamePaging(name, pageable).toPageResponse()
    }

    // 공통 Page Util 을 사용한 function
    @Transactional(readOnly = true)
    fun findByNamePagingUtil(name: String, pageable: Pageable): PageResponse<DslEntity> {
        return dslRepository.findByNamePagingUtil(name, pageable)
    }

}