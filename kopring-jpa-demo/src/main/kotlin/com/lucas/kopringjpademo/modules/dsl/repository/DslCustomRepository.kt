package com.lucas.kopringjpademo.modules.dsl.repository

import com.lucas.kopringjpademo.modules.dsl.entity.DslEntity

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

}