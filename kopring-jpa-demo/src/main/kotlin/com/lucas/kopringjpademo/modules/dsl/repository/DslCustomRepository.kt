package com.lucas.kopringjpademo.modules.dsl.repository

import com.lucas.kopringjpademo.common.PageResponse
import com.lucas.kopringjpademo.modules.dsl.dto.DslResponseDTO
import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

/**
 * DslCustomRepository.kt: QueryDsl 에서 사용할 Custom Repository 인터페이스
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 6. 12. 오전 1:31
 * @description: 
 */
interface DslCustomRepository {

    fun findByNameLike(name: String): List<DslEntity>

    fun findByNameIn(names: List<String>): List<DslEntity>

    // DTO Response
    fun findByAddressLike(address: String): List<DslResponseDTO>

    // 페이징
    fun findByNamePaging(name: String, pageable: Pageable): Page<DslEntity>
    fun findByNamePagingUtil(name: String, pageable: Pageable): PageResponse<DslEntity>
}