package com.lucas.hexagonalkotlin.adapter.out.persistence.post

import org.springframework.data.jpa.repository.JpaRepository

/**
 * PostJpaRepository.kt: JPA Repository
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:42
 * @description: 
 */
interface PostJpaRepository: JpaRepository<PostEntity, Long> {
}