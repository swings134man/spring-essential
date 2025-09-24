package com.lucas.hexagonalkotlin.domain.post.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import com.lucas.hexagonalkotlin.domain.post.model.Post
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
    val writer: String?,
    val delYn: String = "N",
    val viewCount: Int = 0,
    val userId: Long, // fk
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        fun fromDomain(domain: Post): PostDto {
            return PostDto(
                id = domain.id,
                title = domain.title,
                content = domain.content,
                writer = domain.writer,
                delYn = domain.delYn,
                viewCount = domain.viewCount,
                userId = domain.userId,
                createdAt = domain.createdAt,
                updatedAt = domain.updatedAt
            )
        }
    }
}

data class PostCreateDto (
    val title: String,
    val content: String,
    val userId: Long, // fk
//    @JsonIgnore val delYn: String = "N",
//    @JsonIgnore val viewCount: Int = 0
)

data class PostUpdateDto (
    val id: Long,
    val title: String,
    val content: String,
    val userId: Long // fk
)

data class PostDeleteDto (
    val id: Long,
    val userId: Long // fk
)