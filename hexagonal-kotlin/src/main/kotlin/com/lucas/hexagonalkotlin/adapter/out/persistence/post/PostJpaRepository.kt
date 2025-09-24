package com.lucas.hexagonalkotlin.adapter.out.persistence.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

/**
 * PostJpaRepository.kt: JPA Repository
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 25. 오전 12:42
 * @description: 
 */
interface PostJpaRepository: JpaRepository<PostEntity, Long> {

    @Query("SELECT p FROM PostEntity p WHERE p.id = :id AND p.delYn = 'N'")
    fun findActivePostById(@Param("id") id: Long): PostEntity?

    @Modifying
    @Query("UPDATE PostEntity p SET p.delYn = 'Y' WHERE p.id = :id")
    fun deletePostById(@Param("id") id: Long): Int

    @Modifying
    @Query("UPDATE PostEntity p SET p.viewCount = :viewCount WHERE p.id = :id")
    fun increaseViewCount(
        @Param("id") id: Long,
        @Param("viewCount") viewCount: Int
    ): Int
}