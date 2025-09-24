package com.lucas.hexagonalkotlin.domain.post.dto

import java.time.LocalDateTime

/**
 * PostDto.kt: Post 도메인에 관한 DTO
 *
 * @author: lucaskang(swings134man)
 * @since: 2025. 9. 24. 오후 6:12
 * @description: 
 */
data class PostDto (
    val id: Long? = null,
    val title: String,
    val content: String,
    val writer: String,
    val delYn: Char,
    val viewCount: Int,
    val userId: Long, // fk
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
)

data class PostCreateDto (
    val title: String,
    val content: String,
    val userId: Long // fk
)

data class PostUpdateDto (
    val id: Long,
    val title: String,
    val content: String,
    val userId: Long // fk
)

